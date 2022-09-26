package com.wp.system.utils.fns;

import com.wp.system.exception.ServiceException;
import org.springframework.http.HttpStatus;

public class FNSMessageData extends FNSIntegration {
    public FNSMessageData(String tempToken) {
        super(tempToken);
    }

    public String getMessageData(String messageId) {
        try {
            String response = FNSRequestSender.send("open-api/ais3/KktService/0.1", "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-gnivc-ru/inplat/servin/OpenApiAsyncMessageConsumerService/types/1.0\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <ns:GetMessageRequest>\n" +
                    "         <ns:MessageId>" + messageId + "</ns:MessageId>\n" +
                    "      </ns:GetMessageRequest>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>", "POST", this.tempToken, "urn:GetMessageRequest");

            return response;
        } catch (Exception e) {
            throw new ServiceException("Error on sending request to FNS", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
