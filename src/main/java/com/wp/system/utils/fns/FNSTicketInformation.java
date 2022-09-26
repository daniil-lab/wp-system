package com.wp.system.utils.fns;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;

public class FNSTicketInformation extends FNSIntegration {
    public FNSTicketInformation(String tempToken) {
        super(tempToken);
    }

    public String getTicketInformation(Integer sum,
                                     String date,
                                     String fn,
                                     int operationType,
                                     String fiscalDocumentId,
                                     String fiscalSign,
                                     boolean rawData) {
        try {
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage();


        } catch (Exception e) {
            e.printStackTrace();
        }

        String response = FNSRequestSender.send("open-api/ais3/KktService/0.1", "<soap-env:Envelope xmlns:soap-env=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "<soap-env:Body>" +
                "<ns0:SendMessageRequest xmlns:ns0=\"urn://x-artefacts-gnivc-ru/inplat/servin/OpenApiAsyncMessageConsumerService/types/1.0\">" +
                "<ns0:Message>" +
                "<tns:GetTicketRequest xmlns:tns=\"urn://x-artefacts-gnivc-ru/ais3/kkt/KktTicketService/types/1.0\">" +
                "<tns:GetTicketInfo>" +
                "<tns:Sum>" + sum + "</tns:Sum>" +
                "<tns:Date>" + date + "</tns:Date>" +
                "<tns:Fn>" + fn + "</tns:Fn>" +
                "<tns:TypeOperation>" + operationType + "</tns:TypeOperation>" +
                "<tns:FiscalDocumentId>" + fiscalDocumentId + "</tns:FiscalDocumentId>" +
                "<tns:FiscalSign>" + fiscalSign + "</tns:FiscalSign>" +
                "<tns:RawData>true</tns:RawData>" +
                "</tns:GetTicketInfo>" +
                "</tns:GetTicketRequest>" +
                "</ns0:Message>" +
                "</ns0:SendMessageRequest>" +
                "</soap-env:Body>" +
                "</soap-env:Envelope>", "POST", this.tempToken, "urn:SendMessageRequest");

        return response;
    }
}
