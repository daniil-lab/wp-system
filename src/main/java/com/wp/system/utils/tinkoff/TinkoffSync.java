package com.wp.system.utils.tinkoff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wp.system.entity.tinkoff.TinkoffCard;
import com.wp.system.entity.tinkoff.TinkoffIntegration;
import com.wp.system.entity.tinkoff.TinkoffSyncStage;
import com.wp.system.entity.tinkoff.TinkoffTransaction;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.tinkoff.TinkoffCardRepository;
import com.wp.system.repository.tinkoff.TinkoffIntegrationRepository;
import com.wp.system.repository.tinkoff.TinkoffTransactionRepository;
import com.wp.system.utils.BankSync;
import com.wp.system.utils.TrustedHttpClient;
import com.wp.system.utils.WalletType;
import com.wp.system.utils.bill.TransactionType;
import com.wp.system.utils.tinkoff.response.cards.TinkoffCardsContainerResponse;
import com.wp.system.utils.tinkoff.response.cards.TinkoffCardsResponse;
import com.wp.system.utils.tinkoff.response.cards.TinkoffCardsWrapperResponse;
import com.wp.system.utils.tinkoff.response.operations.TinkoffOperationResponse;
import com.wp.system.utils.tinkoff.response.operations.TinkoffOperationsWrapperResponse;
import com.wp.system.utils.tinkoff.response.session.TinkoffSessionStatusResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class TinkoffSync implements BankSync {
    private final RestTemplate restTemplate = new RestTemplate();

    private final TinkoffIntegration integration;

    private final ObjectMapper mapper = new ObjectMapper();

    private TinkoffIntegrationRepository integrationRepository;

    private TinkoffCardRepository cardRepository;

    private TinkoffTransactionRepository transactionRepository;

    public TinkoffSync(TinkoffIntegration integration) {
        this.integration = integration;
    }

    public TinkoffTransaction createTransaction(TinkoffOperationResponse o, TransactionType t) {
        TinkoffTransaction transaction = new TinkoffTransaction();

        if(o.getCard() != null) {
            Optional<TinkoffCard> foundCard = cardRepository.getCardByCardId(o.getCard(), integration.getUser().getId());

            foundCard.ifPresent(transaction::setCard);
        }

        transaction.setAmount(BigDecimal.valueOf(o.getAmount().getValue()));
        transaction.setDescription(o.getDescription());
        transaction.setTransactionType(t);
        transaction.setDate(Instant.ofEpochMilli(o.getOperationTime().getMilliseconds()));
        transaction.setTinkoffId(o.getId());
        transaction.setStatus(o.getStatus());
        transaction.setCurrency(WalletType.valueOf(o.getAmount().getCurrency().getName()));

        return transaction;
    }

    public void getOperations() {
        if(validateSession()) {
            ResponseEntity<TinkoffOperationsWrapperResponse> withdrawResponse = restTemplate.exchange("https://www.tinkoff.ru/api/common/v1/operations?sessionid="
                            + integration.getToken() + "&start=" + integration.getStartDate().toEpochMilli() +
                    "&end=" + Instant.now().toEpochMilli() + "&config=spending",
                    HttpMethod.GET, new HttpEntity<>(null), TinkoffOperationsWrapperResponse.class);

            for (TinkoffOperationResponse o : withdrawResponse.getBody().getPayload()) {
                Optional<TinkoffCard> foundCard = cardRepository.getCardByCardId(o.getCard(), integration.getUser().getId());

                if(foundCard.isPresent()) {
                    Optional<TinkoffTransaction> transactionDuplicate = transactionRepository.getTinkoffTransactionByTinkoffId(o.getId(), foundCard.get().getId());

                    if(transactionDuplicate.isPresent())
                        continue;

                    transactionRepository.save(createTransaction(o, TransactionType.WITHDRAW));
                }
            }

            ResponseEntity<TinkoffOperationsWrapperResponse> earnResponse = restTemplate.exchange("https://www.tinkoff.ru/api/common/v1/operations?sessionid="
                            + integration.getToken() + "&start=" +
                            integration.getStartDate().toEpochMilli() +
                            "&end=" + Instant.now().toEpochMilli() + "&config=earning",
                    HttpMethod.GET, new HttpEntity<>(null), TinkoffOperationsWrapperResponse.class);

            for (TinkoffOperationResponse o : earnResponse.getBody().getPayload()) {
                Optional<TinkoffCard> foundCard = cardRepository.getCardByCardId(o.getCard(), integration.getUser().getId());

                if(foundCard.isPresent()) {
                    Optional<TinkoffTransaction> transactionDuplicate = transactionRepository.getTinkoffTransactionByTinkoffId(o.getId(), foundCard.get().getId());

                    if(transactionDuplicate.isPresent())
                        continue;

                    transactionRepository.save(createTransaction(o, TransactionType.DEPOSIT));
                }
            }

//            integration.setLastOperationsSyncDate(Instant.now().minus(1, ChronoUnit.HOURS));
        }
    }

    public void getCards() {
        if(validateSession()) {
            try {
                ResponseEntity<TinkoffCardsWrapperResponse> response = restTemplate.exchange("https://www.tinkoff.ru/api/common/v1/accounts_flat?sessionid=" + integration.getToken(),
                        HttpMethod.GET, new HttpEntity<>(null), TinkoffCardsWrapperResponse.class);

                System.out.println(response.getStatusCode());
                System.out.println(response.getBody().getPayload().size());

                for (TinkoffCardsContainerResponse cr : response.getBody().getPayload()) {
                    Set<TinkoffCard> validateCards = integration.getCards();

                    for (TinkoffCard c : validateCards) {
                        boolean flag = false;

                        for (TinkoffCardsResponse r : cr.getCardNumbers()) {
                            if(r.getId().equals(c.getCardId())) {
                                flag = true;
                                break;
                            }
                        }

                        if(!flag) {
                            cardRepository.delete(c);
                        }
                    }

                    System.out.println(cr.getCardNumbers().size());

                    for (TinkoffCardsResponse r : cr.getCardNumbers()) {
                        Optional<TinkoffCard> foundCard = cardRepository.getCardByCardId(r.getId(), integration.getUser().getId());

                        if (foundCard.isPresent()) {
                            TinkoffCard card = foundCard.get();

                            card.setBalance(BigDecimal.ZERO);

                            String[] arr= String.valueOf(r.getAvailableBalance().getValue()).split("\\.");

                            card.setBalance(new BigDecimal(String.valueOf(r.getAvailableBalance().getValue())));
                            card.setStatus(r.getStatus());
                            card.setName(r.getName());
                            card.setExpirationMillis(r.getExpiration().getMilliseconds());
                            card.setCurrency(WalletType.valueOf(r.getAvailableBalance().getCurrency().getName()));
                            card.setCreatedMillis(r.getCreationDate().getMilliseconds());
                            cardRepository.save(card);

                            continue;
                        }

                        TinkoffCard newCard = new TinkoffCard();

                        newCard.setCardId(r.getId());
                        newCard.setCardNumber(r.getValue());
                        newCard.setCurrency(WalletType.valueOf(r.getAvailableBalance().getCurrency().getName()));
                        newCard.setIntegration(integration);
                        newCard.setBalance(BigDecimal.valueOf(r.getAvailableBalance().getValue()));
                        newCard.setStatus(r.getStatus());
                        newCard.setName(r.getName());
                        newCard.setExpirationMillis(r.getExpiration().getMilliseconds());
                        newCard.setCreatedMillis(r.getCreationDate().getMilliseconds());

                        cardRepository.save(newCard);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

                this.handleError();
            }

        }
    }

    public boolean validateSession() {
        try {
            ResponseEntity<TinkoffSessionStatusResponse> response = restTemplate.exchange("https://www.tinkoff.ru/api/common/v1/session_status?sessionid=" + integration.getToken(),
                    HttpMethod.GET, new HttpEntity<>(null), TinkoffSessionStatusResponse.class);

            return response.getBody().getPayload().getAccessLevel().equals("CLIENT");
        } catch (Exception e) {
            e.printStackTrace();

            this.handleError();

            return false;
        }
    }

    public void handleError() {
        integration.setStage(TinkoffSyncStage.ERROR);

        integrationRepository.save(integration);

        throw new ServiceException("Auth session expired, need re-auth", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public TinkoffIntegrationRepository getIntegrationRepository() {
        return integrationRepository;
    }

    public void setIntegrationRepository(TinkoffIntegrationRepository integrationRepository) {
        this.integrationRepository = integrationRepository;
    }

    public TinkoffCardRepository getCardRepository() {
        return cardRepository;
    }

    public void setCardRepository(TinkoffCardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public void sync() {
        integration.setStage(TinkoffSyncStage.IN_SYNC);

        integrationRepository.save(integration);

        this.getCards();
        this.getOperations();

        integration.setStage(TinkoffSyncStage.SYNCED);

        integrationRepository.save(integration);
    }

    public TinkoffTransactionRepository getTransactionRepository() {
        return transactionRepository;
    }

    public void setTransactionRepository(TinkoffTransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
}
