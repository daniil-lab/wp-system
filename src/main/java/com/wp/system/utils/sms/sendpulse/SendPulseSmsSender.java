package com.wp.system.utils.sms.sendpulse;

import com.wp.system.exception.ServiceException;
import com.wp.system.utils.sendpulse.SendPulseData;
import com.wp.system.utils.sendpulse.SendPulseIntegration;
import com.wp.system.utils.SmsSender;
import org.springframework.http.HttpStatus;

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
            throw new ServiceException("Body or phone not found", HttpStatus.INTERNAL_SERVER_ERROR);

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
