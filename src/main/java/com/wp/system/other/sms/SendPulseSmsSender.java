package com.wp.system.other.sms;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wp.system.exception.ServiceException;
import com.wp.system.exception.auth.AuthErrorCode;
import com.wp.system.exception.system.SystemErrorCode;
import com.wp.system.other.sendpulse.SendPulseData;
import com.wp.system.other.sendpulse.SendPulseIntegration;
import com.wp.system.other.SmsSender;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class SendPulseSmsSender extends SendPulseIntegration implements SmsSender {

    private String phone;

    private String body;

    public SendPulseSmsSender() {
        super();
    }

    @Override
    public boolean send() {
        if(phone == null || body == null)
            return false;

        try {
            Map<String, Object> data = new HashMap<>();

            data.put("sender", SendPulseData.SMS_SENDER_NAME);
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
