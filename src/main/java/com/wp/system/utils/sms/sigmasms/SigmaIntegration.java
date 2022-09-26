package com.wp.system.utils.sms.sigmasms;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wp.system.exception.ServiceException;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class SigmaIntegration {
    protected final String ACCESS_TOKEN;

    protected final String API_URL = "https://online.sigmasms.ru/api/";

    private final String username = System.getenv("SIGMA_USER");

    private final String password = System.getenv("SIGMA_PASS");

    protected final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public SigmaIntegration() {
        this.ACCESS_TOKEN = this.getToken();
    }

    protected String getToken() {
        try {
            Map<String, Object> data = new HashMap<>();

            data.put("username", username);
            data.put("password", password);

            HttpRequest authRequest = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + "login"))
                    .method("POST", HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(data)))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> authResponse = HttpClient.newBuilder().build().send(authRequest, HttpResponse.BodyHandlers.ofString());

            Map<String, Object> responseData = mapper.readValue(authResponse.body(), new TypeReference<Map<String, Object>>() {});

            return (String) responseData.get("token");
        } catch (Exception e) {
            throw new ServiceException("Sigma integration auth error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
