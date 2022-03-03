package com.wp.system.utils.sendpulse;

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

public class SendPulseIntegration {
    protected final String BEARER_HEADER_PREFIX = "Bearer ";

    protected final String API_URL = "https://api.sendpulse.com/";

    protected final String CLIENT_ID = System.getenv("SENDPULSE_CLIENT_ID");

    protected final String CLIENT_SECRET = System.getenv("SENDPULSE_CLIENT_SECRET");

    protected final String GRANT_TYPE = "client_credentials";

    protected final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    protected final String ACCESS_TOKEN;

    public SendPulseIntegration() {
        this.ACCESS_TOKEN = this.getToken();
    }

    protected String getToken() {
        try {
            Map<String, Object> data = new HashMap<>();

            data.put("client_id", CLIENT_ID);
            data.put("client_secret", CLIENT_SECRET);
            data.put("grant_type", GRANT_TYPE);

            HttpRequest authRequest = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + "oauth/access_token"))
                    .method("POST", HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(data)))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> authResponse = HttpClient.newBuilder().build().send(authRequest, HttpResponse.BodyHandlers.ofString());

            Map<String, Object> responseData = mapper.readValue(authResponse.body(), new TypeReference<Map<String, Object>>() {});

            return (String) responseData.get("access_token");
        } catch (Exception e) {
            throw new ServiceException("SendPulse integration auth error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
