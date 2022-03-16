package com.wp.system.utils.sber;

import com.wp.system.entity.sber.SberIntegration;
import com.wp.system.repository.sber.SberIntegrationRepository;
import com.wp.system.utils.BankSync;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class SberSync implements BankSync {
    private final RestTemplate restTemplate = new RestTemplate();

    private SberIntegration sberIntegration;

    private SberIntegrationRepository sberIntegrationRepository;

    public SberSync(SberIntegration sberIntegration, SberIntegrationRepository sberIntegrationRepository) {
        this.sberIntegration = sberIntegration;
        this.sberIntegrationRepository = sberIntegrationRepository;
    }

    @Override
    public void sync() {
        System.out.println(getCards());
    }

    private String getCards() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Set-Cookie", sberIntegration.getSession());

        MultiValueMap<String, String> confirmRequestBody = new LinkedMultiValueMap<String, String>();
        confirmRequestBody.add("showProductType", "cards");

        HttpEntity<MultiValueMap<String, String>> confirmRequest = new HttpEntity<MultiValueMap<String, String>>(confirmRequestBody, headers);

        ResponseEntity<String> confirmResponse = restTemplate.postForEntity( "https://node2.online.sberbank.ru:4477/mobile9/private/products/list.do", confirmRequest , String.class);

        return confirmResponse.getBody();
    }
}
