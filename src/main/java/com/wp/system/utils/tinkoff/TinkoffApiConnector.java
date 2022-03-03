package com.wp.system.utils.tinkoff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wp.system.entity.tinkoff.TinkoffCard;
import com.wp.system.entity.tinkoff.TinkoffIntegration;
import com.wp.system.repository.tinkoff.TinkoffCardRepository;
import com.wp.system.repository.tinkoff.TinkoffIntegrationRepository;
import com.wp.system.utils.TrustedHttpClient;
import com.wp.system.utils.WalletType;
import com.wp.system.utils.tinkoff.response.cards.TinkoffCardsContainerResponse;
import com.wp.system.utils.tinkoff.response.cards.TinkoffCardsResponse;
import com.wp.system.utils.tinkoff.response.cards.TinkoffCardsWrapperResponse;
import com.wp.system.utils.tinkoff.response.session.TinkoffSessionStatusResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class TinkoffApiConnector {
    private final RestTemplate restTemplate = new RestTemplate();

    private final TinkoffIntegration integration;

    private final ObjectMapper mapper = new ObjectMapper();

    private TinkoffIntegrationRepository integrationRepository;

    private TinkoffCardRepository cardRepository;

    public TinkoffApiConnector(TinkoffIntegration integration) {
        this.integration = integration;
    }

    public void getOperations(Long startTime, Long endTime) {
        if(validateSession()) {

        }
    }

    public void getCards() {
        if(validateSession()) {
            try {
                ResponseEntity<TinkoffCardsWrapperResponse> response = restTemplate.exchange("https://www.tinkoff.ru/api/common/v1/accounts_flat?sessionid=" + integration.getToken(),
                        HttpMethod.GET, new HttpEntity<>(null), TinkoffCardsWrapperResponse.class);


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

                    for (TinkoffCardsResponse r : cr.getCardNumbers()) {
                        Optional<TinkoffCard> foundCard = cardRepository.getCardByCardId(r.getId());

                        if (foundCard.isPresent()) {
                            TinkoffCard card = foundCard.get();

                            card.getBalance().setAmount(0);
                            card.getBalance().setCents(0);

                            String[] arr= String.valueOf(r.getAvailableBalance().getValue()).split("\\.");

                            card.getBalance().deposit(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
                            card.setStatus(r.getStatus());
                            card.setName(r.getName());
                            card.setExpirationMillis(r.getExpiration().getMilliseconds());
                            card.setCurrency(WalletType.valueOf(r.getAvailableBalance().getCurrency().getName()));

                            cardRepository.save(card);

                            continue;
                        }

                        TinkoffCard newCard = new TinkoffCard();

                        newCard.setCardId(r.getId());
                        newCard.setCardNumber(r.getValue());
                        newCard.setCurrency(WalletType.valueOf(r.getAvailableBalance().getCurrency().getName()));
                        String[] arr= String.valueOf(r.getAvailableBalance().getValue()).split("\\.");
                        newCard.setIntegration(integration);
                        newCard.getBalance().deposit(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
                        newCard.setStatus(r.getStatus());
                        newCard.setName(r.getName());
                        newCard.setExpirationMillis(r.getExpiration().getMilliseconds());

                        cardRepository.save(newCard);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
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

            return false;
        }
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
}
