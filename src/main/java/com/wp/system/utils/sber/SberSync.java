package com.wp.system.utils.sber;

import com.wp.system.entity.sber.SberCard;
import com.wp.system.entity.sber.SberIntegration;
import com.wp.system.entity.sber.SberIntegrationState;
import com.wp.system.entity.sber.SberTransaction;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.sber.SberCardRepository;
import com.wp.system.repository.sber.SberIntegrationRepository;
import com.wp.system.repository.sber.SberTransactionRepository;
import com.wp.system.utils.BankSync;
import com.wp.system.utils.WalletType;
import com.wp.system.utils.bill.TransactionType;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SberSync implements BankSync  {
    private final RestTemplate restTemplate = new RestTemplate();

    private SberIntegrationRepository sberIntegrationRepository;

    private SberIntegration sberIntegration;

    private SberCardRepository sberCardRepository;

    private SberTransactionRepository sberTransactionRepository;

    private UUID userId;

    private int pageOffset = 0;

    private List<SberTransaction> transactions = new ArrayList<>();

    private List<SberCard> cards = new ArrayList<>();

    public SberSync(UUID userId, SberCardRepository sberCardRepository, SberTransactionRepository sberTransactionRepository, SberIntegrationRepository sberIntegrationRepository) {
        this.userId = userId;
        this.sberCardRepository = sberCardRepository;
        this.sberTransactionRepository = sberTransactionRepository;
        this.sberIntegrationRepository = sberIntegrationRepository;
    }

    @Override
    public void sync() {
        try {
            this.sberIntegration = sberIntegrationRepository.getSberIntegrationByUserId(userId).orElseThrow(() -> {
                throw new ServiceException("Integration not found", HttpStatus.BAD_REQUEST);
            });

            if(sberIntegration.getState() == SberIntegrationState.SYNC)
                throw new ServiceException("Invalid state", HttpStatus.BAD_REQUEST);

            sberIntegration.setState(SberIntegrationState.SYNC);

            sberIntegrationRepository.save(sberIntegration);

            syncCards();
            syncTransactions();

            System.out.println(transactions.size());

            saveData();
        } catch (Exception e) {
            e.printStackTrace();
            handleError();
        }
    }

    private void saveData() {
        this.sberIntegration = sberIntegrationRepository.getSberIntegrationByUserId(userId).orElseThrow(() -> {
            throw new ServiceException("Integration not found", HttpStatus.BAD_REQUEST);
        });

        sberIntegration.setState(SberIntegrationState.DEFAULT);

        sberIntegrationRepository.save(sberIntegration);

        cards.forEach(sberCardRepository::save);
        transactions.forEach(sberTransactionRepository::save);
    }

    private void handleError() {
        this.sberIntegration = sberIntegrationRepository.getSberIntegrationByUserId(userId).orElseThrow(() -> {
            throw new ServiceException("Integration not found", HttpStatus.BAD_REQUEST);
        });

        sberIntegration.setState(SberIntegrationState.ERROR_SYNC);

        sberIntegrationRepository.save(sberIntegration);
    }

    private String syncRequest(String cardId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Cookie", sberIntegration.getSession());

        ResponseEntity<String> getTransactionsResponse = restTemplate.exchange("https://" + sberIntegration.getHost() +"/mobile9/private/payments/list.do?" +
                "from=" + SberDateConverter.getStringByInstant(sberIntegration.getStartDate()) +
                "&to=" + SberDateConverter.getStringByInstant(Instant.now()) +
                "&usedResource=card:" + cardId +
                "&showExternal=true" +
                "&includeUfs=true" +
                "&paginationSize=50" +
                "&paginationOffset=" + pageOffset, HttpMethod.POST, new HttpEntity<>(headers), String.class);

        Integer responseCode = SberUtils.getCodeFromResponse(getTransactionsResponse.getBody());

        if(responseCode == null || responseCode != 0)
            throw new ServiceException("Error on getting SBER transactions", HttpStatus.BAD_REQUEST);

        return getTransactionsResponse.getBody();
    }

    private void syncTransactions() {
        try {
            for (SberCard card : cards) {
                System.out.println(card.getCardId());

                boolean flag = true;

                while(flag) {
                    NodeList transactionList = (NodeList) SberUtils.getListDataFromResponse(syncRequest(card.getCardId()), "operation");

                    if(transactionList == null || transactionList.getLength() == 0)
                        flag = false;

                    for (int i = 0; i < transactionList.getLength(); i++) {
                        Element el = (Element) transactionList.item(i);

                        if(el == null)
                            continue;

                        String id = el.getElementsByTagName("id").item(0).getTextContent();

                        if(id == null)
                            continue;

                        Element amount = (Element) el.getElementsByTagName("operationAmount").item(0);

                        if(amount == null)
                            continue;

                        Element amountCurrency = (Element) amount.getElementsByTagName("currency").item(0);

                        if(amountCurrency == null)
                            continue;

                        SberTransaction sberTransaction = null;

                        Optional<SberTransaction> transactionDuplicate = sberTransactionRepository.findByCardIdAndCardIntegrationIdAndSberId(card.getId(), card.getIntegration().getId(), id);

                        sberTransaction = transactionDuplicate.orElseGet(SberTransaction::new);

                        sberTransaction.setSberId(id);
                        sberTransaction.setDescription(el.getElementsByTagName("description").item(0).getTextContent());
                        sberTransaction.setStatus(el.getElementsByTagName("state").item(0).getTextContent());
                        sberTransaction.setDate(SberDateConverter.getInstantByString(el.getElementsByTagName("date").item(0).getTextContent()));

                        WalletType walletType = null;

                        try {
                            walletType = WalletType.valueOf(amountCurrency.getElementsByTagName("code").item(0).getTextContent());
                        } catch (Exception ignored) {

                        }

                        String wallet = amountCurrency.getElementsByTagName("code").item(0).getTextContent();

                        if(wallet.equals("RUR"))
                            walletType = WalletType.RUB;

                        sberTransaction.setCurrency(wallet.equals("RUR") ? WalletType.RUB : WalletType.valueOf(wallet));

                        Double transactionAmount = Double.parseDouble(amount.getElementsByTagName("amount").item(0).getTextContent());
                        sberTransaction.setTransactionType(transactionAmount < 0 ? TransactionType.WITHDRAW : TransactionType.DEPOSIT);
                        sberTransaction.setAmount(new BigDecimal(amount.getElementsByTagName("amount").item(0).getTextContent()));
                        sberTransaction.setCard(card);

                        transactions.add(sberTransaction);

                        pageOffset += 50;
                    }
                }

                System.out.println("TRANSACTIONS");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String syncCards() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.set("Cookie", sberIntegration.getSession());

            ResponseEntity<String> getCardResponse = restTemplate.exchange( "https://" + sberIntegration.getHost() + "/mobile9/private/products/list.do?showProductType=cards,accounts,imaccounts,loans", HttpMethod.POST, new HttpEntity<>(headers) , String.class);

            Integer responseCode = SberUtils.getCodeFromResponse(getCardResponse.getBody());

            if(responseCode == null || responseCode != 0)
                throw new ServiceException("Error on getting SBER card", HttpStatus.BAD_REQUEST);

            NodeList cardList = (NodeList) SberUtils.getListDataFromResponse(getCardResponse.getBody(), "card");

            for(int i = 0; i < cardList.getLength(); i++) {
                Element el = (Element) cardList.item(i);

                String cardId = el.getElementsByTagName("id").item(0).getTextContent();

                Optional<SberCard> foundCard = sberCardRepository.findByCardIdAndIntegrationId(cardId, sberIntegration.getId());

                SberCard card = null;

                card = foundCard.orElseGet(SberCard::new);

                Element balanceEl = (Element) el.getElementsByTagName("availableLimit").item(0);
                Element balanceCurrencyEl = (Element) balanceEl.getElementsByTagName("currency").item(0);

                card.setName(el.getElementsByTagName("name").item(0).getTextContent());
                card.setDescription(el.getElementsByTagName("description").item(0).getTextContent());
                card.setCardNumber(el.getElementsByTagName("number").item(0).getTextContent());
                card.setCardId(cardId);
                card.setExpireDate(el.getElementsByTagName("expireDate").item(0).getTextContent());
                card.setCardAccount(el.getElementsByTagName("cardAccount").item(0).getTextContent());
                card.setStatus(el.getElementsByTagName("state").item(0).getTextContent());
                card.setCurrency(WalletType.valueOf(balanceCurrencyEl.getElementsByTagName("code").item(0).getTextContent()));
                card.setBalance(new BigDecimal(balanceEl.getElementsByTagName("amount").item(0).getTextContent()));
                card.setIntegration(sberIntegration);

                cards.add(card);
            }

            System.out.println("CARDS");

            return getCardResponse.getBody();
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
