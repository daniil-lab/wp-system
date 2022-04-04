package com.wp.system.services.tochka;

import com.wp.system.entity.tinkoff.TinkoffIntegration;
import com.wp.system.entity.tochka.TochkaCard;
import com.wp.system.entity.tochka.TochkaIntegration;
import com.wp.system.entity.user.User;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.tochkaa.*;
import com.wp.system.repository.user.UserRepository;
import com.wp.system.request.tochka.CreateTochkaIntegrationRequest;
import com.wp.system.utils.tochka.TochkaSync;
import com.wp.system.utils.tochka.request.TochkaAuthRequest;
import com.wp.system.utils.tochka.response.TochkaAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.UUID;

@Service
public class TochkaService {

    @Autowired
    private TochkaIntegrationRepository tochkaIntegrationRepository;

    private UserRepository userRepository;

    @Autowired
    private TochkaCardRepository tochkaCardRepository;

    @Transactional
    public TochkaIntegration removeIntegration(UUID userId) {
        TochkaIntegration integration = tochkaIntegrationRepository.getTochkaIntegrationByUserId(userId).orElseThrow(() -> {
            throw new ServiceException("Integration not found", HttpStatus.NOT_FOUND);
        });
        integration.setUser(null);

        tochkaIntegrationRepository.delete(integration);

        return integration;
    }

    public TochkaIntegration submitCreate(CreateTochkaIntegrationRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> {
            throw new ServiceException("User not found", HttpStatus.NOT_FOUND);
        });

        TochkaAuthRequest authRequest = new TochkaAuthRequest();

        authRequest.setClient_id("C1VLYt1nQC8QPtDPyVPf9pfNsRXXCrom");
        authRequest.setRefresh_token(request.getCode());
        authRequest.setGrant_type("authorization_code");
        authRequest.setClient_secret("d4RRWOBeIo9nstgNuEEcy75LD9VJwzHn");

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<TochkaAuthResponse> tochkaResponse = restTemplate.exchange("https:///enter.tochka.com/api/v1/oauth2/token",
                HttpMethod.POST,
                new HttpEntity<>(authRequest, null),
                TochkaAuthResponse.class);

        TochkaIntegration integration = new TochkaIntegration();

        integration.setRefreshToken(tochkaResponse.getBody().getRefresh_token());
        integration.setStartDate(request.getStartDate());
        integration.setUser(user);

        tochkaIntegrationRepository.save(integration);

        return integration;
    }

    public Boolean sync(UUID userId) {
        TochkaIntegration integration = tochkaIntegrationRepository.getTochkaIntegrationByUserId(userId).orElseThrow(() -> {
            throw new ServiceException("Integration not found", HttpStatus.NOT_FOUND);
        });

        new Thread(() -> {
            new TochkaSync(integration, tochkaCardRepository).sync();
        }).start();

        return true;
    }

    public TochkaIntegration getIntegration(UUID userId) {
        TochkaIntegration integration = tochkaIntegrationRepository.getTochkaIntegrationByUserId(userId).orElseThrow(() -> {
            throw new ServiceException("Integration not found", HttpStatus.NOT_FOUND);
        });

        return integration;
    }

    public Set<TochkaCard> getCards(UUID userId) {
        TochkaIntegration integration = tochkaIntegrationRepository.getTochkaIntegrationByUserId(userId).orElseThrow(() -> {
            throw new ServiceException("Integration not found", HttpStatus.NOT_FOUND);
        });

        return integration.getCards();
    }
}
