package com.wp.system.utils.tochka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wp.system.entity.tochka.TochkaCard;
import com.wp.system.entity.tochka.TochkaIntegration;
import com.wp.system.exception.ServiceException;
import com.wp.system.utils.BankSync;
import com.wp.system.utils.WalletType;
import com.wp.system.utils.tochka.request.TochkaAuthRequest;
import com.wp.system.utils.tochka.request.TochkaStartGetTransactionRequest;
import com.wp.system.utils.tochka.response.*;
import okhttp3.Headers;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import com.wp.system.repository.tochkaa.*;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class TochkaSync implements BankSync {
    private TochkaIntegration integration;

    private RestTemplate restTemplate = new RestTemplate();

    private String token;

    private ObjectMapper mapper = new ObjectMapper();

    private List<TochkaCard> cards = new ArrayList<>();

    private TochkaCardRepository cardRepository;

    private int transactionValidateCount = 0;

    private String getTransactionRequestId;

    @Override
    public void sync() {
        try {
            updateToken();
            syncCards();

            for (TochkaCard c : cards)
                syncTransactions(c);

            pushData();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
    private void syncTransactions(TochkaCard c) throws InterruptedException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.add("Authorization", "Bearer " + token);
        if(transactionValidateCount == 0) {
            TochkaStartGetTransactionRequest request = new TochkaStartGetTransactionRequest();

            request.setBank_code(c.getBik());
            request.setAccount_code(c.getCardNumber());
            request.setDate_end(TochkaDateConverter.getStringByInstant(Instant.now()));
            request.setDate_start(TochkaDateConverter.getStringByInstant(c.getIntegration().getLastOperationsSyncDate() == null ? c.getIntegration().getStartDate() :
                    c.getIntegration().getLastOperationsSyncDate()));

            HttpEntity startRequestData = new HttpEntity(request, headers);


            ResponseEntity<TochkaStartGetTransactionResponse> startResponse =  restTemplate.exchange("https://enter.tochka.com/api/v1/statement",
                    HttpMethod.POST,
                    startRequestData,
                    TochkaStartGetTransactionResponse.class);

            if(startResponse.getBody().getRequest_id() == null)
                throw new ServiceException("Invalid tochka response", HttpStatus.INTERNAL_SERVER_ERROR);

            getTransactionRequestId = startResponse.getBody().getRequest_id();

            Thread.sleep(60 * 100);
        }

        if(transactionValidateCount > 3) {
            throw new ServiceException("Get transactions timeout", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ResponseEntity<TochkaStatusResponse> transactionStatusResponse = restTemplate.exchange("https://enter.tochka.com/api/v1/statement/status/" + getTransactionRequestId,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                TochkaStatusResponse.class);

        if(transactionStatusResponse.getBody().getStatus().equals("ready")) {
            ResponseEntity<TochkaTransactionResultResponse> resultResponse = restTemplate.exchange("https://enter.tochka.com/api/v1/statement/result/" + getTransactionRequestId,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    TochkaTransactionResultResponse.class);

            System.out.println(resultResponse);
        } else {
            Thread.sleep(60 * 100);
            transactionValidateCount++;
            syncTransactions(c);
        }
    }

    private void syncCards() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

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

            card = duplicate.orElseGet(TochkaCard::new);

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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        request.setClient_id("C1VLYt1nQC8QPtDPyVPf9pfNsRXXCrom");
        request.setRefresh_token(integration.getRefreshToken());
        request.setGrant_type("refresh_token");
        request.setClient_secret("d4RRWOBeIo9nstgNuEEcy75LD9VJwzHn");

        HttpEntity requestData = new HttpEntity(request, headers);

        ResponseEntity<TochkaAuthResponse> updateTokenResponse = restTemplate.exchange("https://enter.tochka.com/api/v1/oauth2/token",
                HttpMethod.POST,
                requestData,
                TochkaAuthResponse.class);

        this.token = updateTokenResponse.getBody().getRefresh_token();
    }
}
