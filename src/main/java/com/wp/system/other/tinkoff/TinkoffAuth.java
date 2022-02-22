package com.wp.system.other.tinkoff;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TinkoffAuth {
    public void auth() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.getForEntity("https://tinkoff.ru/login/", String.class);

        System.out.println(response.getStatusCode());
        System.out.println(response.getHeaders().getHost());
        System.out.println(response.getBody());
    }
}
