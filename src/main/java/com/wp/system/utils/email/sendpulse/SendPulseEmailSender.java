package com.wp.system.utils.email.sendpulse;

import com.wp.system.exception.ServiceException;
import com.wp.system.utils.EmailSender;
import com.wp.system.utils.EmailCredData;
import com.wp.system.utils.sendpulse.SendPulseData;
import com.wp.system.utils.sendpulse.SendPulseIntegration;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class SendPulseEmailSender extends SendPulseIntegration implements EmailSender {
    private String body;

    private String subject;

    private EmailCredData from = new EmailCredData();

    private EmailCredData to = new EmailCredData();

    private List<SendPulseEmailFile> fileList = new ArrayList<>();

    public SendPulseEmailSender() {
        super();
    }

    @Override
    public boolean sendEmail() {
        if(to == null || subject == null || body == null)
            return false;

        if(this.from.getEmail() == null || this.from.getName() == null) {
            this.from.setEmail(SendPulseData.EMAIL_SENDER_EMAIL);
            this.from.setName(SendPulseData.EMAIL_SENDER_NAME);
        }

        try {
            System.out.println(this.ACCESS_TOKEN);

            Map<String, Object> data = new HashMap<>();
            Map<String, Object> senderData = new HashMap<>();
            Map<String, Object> clientData = new HashMap<>();
            List<Map> clients = new ArrayList<>();
            List<Map> files = new ArrayList<>();

            senderData.put("name", this.from.getName());
            senderData.put("email", this.from.getEmail());

            clientData.put("name", this.to.getName());
            clientData.put("email", this.to.getEmail());

            clients.add(clientData);

            for (SendPulseEmailFile file : fileList) {
                Map<String, Object> fileData = new HashMap<>();

                fileData.put(file.getFileName(), file.getFile());
            }

            data.put("html", new String(Base64.getEncoder().encode(this.body.getBytes())));
            data.put("text", this.body);
            data.put("subject", this.subject);
            data.put("to", clients);
            data.put("from", senderData);

            System.out.println(mapper.writeValueAsString(data));

            HttpRequest sendRequest = HttpRequest.newBuilder()
                    .uri(URI.create(this.API_URL + "smtp/emails"))
                    .method("POST", HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(data)))
                    .header("Content-Type", "application/json")
                    .header("Authorization", this.BEARER_HEADER_PREFIX + this.ACCESS_TOKEN)
                    .build();

            HttpResponse<String> sendResponse = HttpClient.newBuilder().build().send(sendRequest, HttpResponse.BodyHandlers.ofString());

            System.out.println(sendResponse.body());

            if(sendResponse.statusCode() != 200)
                throw new ServiceException("Error on send mail", HttpStatus.INTERNAL_SERVER_ERROR);

            return true;
        } catch (Exception e) {
            throw new ServiceException("Error on send mail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void addBody(String text) {
        this.body = text;
    }

    @Override
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public void setFrom(String name, String email) {
        this.from.setEmail(email);
        this.from.setEmail(name);
    }

    @Override
    public void setTo(String name, String email) {
        this.to.setEmail(email);
        this.to.setName(name);
    }

    @Override
    public void addFile(String fileName, byte[] file) {
        this.fileList.add(new SendPulseEmailFile(fileName, file));
    }
}
