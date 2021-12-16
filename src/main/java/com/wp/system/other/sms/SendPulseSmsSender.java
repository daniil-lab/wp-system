package com.wp.system.other.sms;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wp.system.exception.ServiceException;
import com.wp.system.exception.auth.AuthErrorCode;
import com.wp.system.exception.system.SystemErrorCode;
import com.wp.system.other.SmsSender;
import com.wp.system.other.WalletType;
import com.wp.system.other.sms.sendpulse.AddressBookResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.tomcat.jni.Address;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendPulseSmsSender implements SmsSender {
    private final String BEARER_HEADER_PREFIX = "Bearer ";

    private final String API_URL = "https://api.sendpulse.com/";

    private final String CLIENT_ID = "d9bdd184a2a4a14b830a8b264ce33f31";

    private final String CLIENT_SECRET = "e6a25e4d987db74d7e63543d9ca429c1";

    private final String GRANT_TYPE = "client_credentials";

    private String ACCESS_TOKEN;

    private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private String phone;

    private String body;

    public SendPulseSmsSender() {
        this.getAccessToken();
    }

    private void getAccessToken() {
        try {
            Map<String, Object> data = new HashMap<>();

            data.put("client_id", this.CLIENT_ID);
            data.put("client_secret", this.CLIENT_SECRET);
            data.put("grant_type", this.GRANT_TYPE);

            HttpRequest authRequest = HttpRequest.newBuilder()
                    .uri(URI.create(this.API_URL + "oauth/access_token"))
                    .method("POST", HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(data)))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> authResponse = HttpClient.newBuilder().build().send(authRequest, HttpResponse.BodyHandlers.ofString());

            Map<String, Object> responseData = mapper.readValue(authResponse.body(), new TypeReference<Map<String, Object>>() {});

            this.ACCESS_TOKEN = (String) responseData.get("access_token");
        } catch (Exception e) {
            throw new ServiceException(SystemErrorCode.INTERNAL_ERROR);
        }
    }

    @Override
    public boolean send() {
        if(phone == null || body == null)
            return false;

        try {
            Map<String, Object> data = new HashMap<>();

            data.put("sender", "WP");
            data.put("phones", new String[] { this.phone });
            data.put("body", this.body);
            data.put("transliterate", 0);

            HttpRequest sendRequest = HttpRequest.newBuilder()
                    .uri(URI.create(this.API_URL + "sms/send"))
                    .method("POST", HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(data)))
                    .header("Content-Type", "application/json")
                    .header("Authorization", this.BEARER_HEADER_PREFIX + this.ACCESS_TOKEN)
                    .build();

            HttpResponse<String> sendResponse = HttpClient.newBuilder().build().send(sendRequest, HttpResponse.BodyHandlers.ofString());

            if(sendResponse.statusCode() != 200)
                throw new ServiceException(AuthErrorCode.SMS_NOT_SEND);

            return true;
        } catch (Exception e) {
            throw new ServiceException(SystemErrorCode.INTERNAL_ERROR);
        }
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public void setContent(String content) {
        this.body = content;
    }
}
