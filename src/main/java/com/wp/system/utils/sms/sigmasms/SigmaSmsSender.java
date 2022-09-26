package com.wp.system.utils.sms.sigmasms;

import com.wp.system.exception.ServiceException;
import com.wp.system.utils.SmsSender;
import com.wp.system.utils.sendpulse.SendPulseData;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class SigmaSmsSender extends SigmaIntegration implements SmsSender {
    private String phone;

    private String body;

    public SigmaSmsSender() {
        super();
    }

    @Override
    public boolean send() {
        if(phone == null || body == null)
            throw new ServiceException("Body or phone not found", HttpStatus.INTERNAL_SERVER_ERROR);

        try {
            Map<String, Object> data = new HashMap<>();
            Map<String, Object> smsData = new HashMap<>();

            data.put("recipient", this.phone);
            data.put("type", "sms");

            smsData.put("sender", "B-Media");
            smsData.put("text", body);

            data.put("payload", smsData);

            System.out.println(mapper.writeValueAsString(data));

            HttpRequest sendRequest = HttpRequest.newBuilder()
                    .uri(URI.create(this.API_URL + "sendings"))
                    .method("POST", HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(data)))
                    .header("Content-Type", "application/json")
                    .header("Authorization", this.ACCESS_TOKEN)
                    .build();

            HttpResponse<String> sendResponse = HttpClient.newBuilder().build().send(sendRequest, HttpResponse.BodyHandlers.ofString());

            System.out.println(sendResponse.body());

            if(sendResponse.statusCode() != 200)
                throw new ServiceException("Sms does`t send", HttpStatus.INTERNAL_SERVER_ERROR);

            return true;
        } catch (Exception e) {
            throw new ServiceException("Sms does`t send", HttpStatus.INTERNAL_SERVER_ERROR);
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
