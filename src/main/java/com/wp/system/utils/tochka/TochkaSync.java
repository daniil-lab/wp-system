package com.wp.system.utils.tochka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wp.system.entity.tochka.TochkaCard;
import com.wp.system.entity.tochka.TochkaIntegration;
import com.wp.system.exception.ServiceException;
import com.wp.system.utils.BankSync;
import com.wp.system.utils.WalletType;
import com.wp.system.utils.tochka.request.TochkaAuthRequest;
import com.wp.system.utils.tochka.response.TochkaAuthResponse;
import com.wp.system.utils.tochka.response.TochkaCardResponse;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import com.wp.system.repository.tochkaa.*;

import java.util.*;
import java.util.stream.Collectors;

public class TochkaSync implements BankSync {
    private TochkaIntegration integration;

    private RestTemplate restTemplate = new RestTemplate();

    private String token;

    private ObjectMapper mapper = new ObjectMapper();

    private List<TochkaCard> cards = new ArrayList<>();

    private TochkaCardRepository cardRepository;

    @Override
    public void sync() {
        updateToken();
        syncCards();
        pushData();
    }

    public TochkaSync(TochkaIntegration integration, TochkaCardRepository cardRepository) {
        this.integration = integration;
        this.cardRepository = cardRepository;
    }

    //    private void getTransactionRequest(TochkaCard card) {
//        ResponseEntity getTransactionResponse = restTemplate.exchange("https://enter.tochka.com/api/v1/statement", HttpMethod.GET,
//                )
//    }
//
//    private void syncTransactions() {
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                syncTransactions();
//            }
//        }, 300L);
//    }

    private void syncCards() {
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Bearer " + token);

        HttpEntity requestData = new HttpEntity(headers);

        ResponseEntity<Object[]> cardResponse = restTemplate.exchange("https://enter.tochka.com/api/v1/account/list",
                HttpMethod.GET,
                requestData,
                Object[].class);

        List<TochkaCardResponse> cardResponses = Arrays.stream(cardResponse.getBody())
                .map(object -> mapper.convertValue(object, TochkaCardResponse.class))
                .collect(Collectors.toList());

        cardResponses.forEach(resp -> {
            TochkaCard card = null;

            Optional<TochkaCard> duplicate = cardRepository.findByCardNumberAndIntegrationId(resp.getCode(), integration.getId());

            if(duplicate.isPresent())
                card = duplicate.get();
            else
                card = new TochkaCard();

            card.setIntegration(integration);
            card.setCardNumber(resp.getCode());
            card.setCurrency(WalletType.RUB);
            card.setBik(resp.getBank_code());

            cards.add(card);
        });
    }

    private void pushData() {
        for (TochkaCard c : cards) {
            cardRepository.save(c);
        }
    }

    private void updateToken() {
        if(integration.getRefreshToken() == null)
            throw new ServiceException("Refresh token not found. Relogin.", HttpStatus.INTERNAL_SERVER_ERROR);

        TochkaAuthRequest request = new TochkaAuthRequest();

        request.setClient_id("C1VLYt1nQC8QPtDPyVPf9pfNsRXXCrom");
        request.setRefresh_token(integration.getRefreshToken());
        request.setGrant_type("refresh_token");
        request.setClient_secret("d4RRWOBeIo9nstgNuEEcy75LD9VJwzHn");

        HttpEntity requestData = new HttpEntity(request, null);

        ResponseEntity<TochkaAuthResponse> updateTokenResponse = restTemplate.exchange("https://enter.tochka.com/api/v1/oauth2/token",
                HttpMethod.POST,
                requestData,
                TochkaAuthResponse.class);

        this.token = updateTokenResponse.getBody().getRefresh_token();
    }
}
