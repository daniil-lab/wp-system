package com.wp.system.other.tinkoff;

import com.wp.system.other.tinkoff.response.TinkoffTokenResponse;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class TinkoffAuthIntegration {
    public static TinkoffTokenResponse completeAuth(String code) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type","authorization_code");
        map.add("redirect_uri","https://myintegration.ru/auth/complete&code=" + code);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, httpHeaders);

        ResponseEntity<TinkoffTokenResponse> response = restTemplate.exchange(
                "https://id.tinkoff.ru/auth/token",
                HttpMethod.POST,
                entity,
                TinkoffTokenResponse.class
        );

        System.out.println(response.getStatusCode());
        System.out.println(response.getBody().getAccess_token());

        return response.getBody();
    }
}
