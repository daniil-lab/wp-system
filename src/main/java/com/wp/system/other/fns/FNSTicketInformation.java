package com.wp.system.other.fns;

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
                "   <soap-env:Body>\n" +
                "      <ns0:SendMessageRequest xmlns:ns0=\"urn://x-artefacts-gnivc-ru/inplat/servin/OpenApiAsyncMessageConsumerService/types/1.0\">\n" +
                "         <ns0:Message>\n" +
                "            <tns:GetTicketRequest xmlns:tns=\"urn://x-artefacts-gnivc-ru/ais3/kkt/KktTicketService/types/1.0\">\n" +
                "               <tns:GetTicketInfo>\n" +
                "                  <tns:Sum>" + sum + "</tns:Sum>\n" +
                "                  <tns:Date>" + date + "</tns:Date>\n" +
                "                  <tns:Fn>" + fn + "</tns:Fn>\n" +
                "                  <tns:TypeOperation>" + operationType + "</tns:TypeOperation>\n" +
                "                  <tns:FiscalDocumentId>" + fiscalDocumentId + "</tns:FiscalDocumentId>\n" +
                "                  <tns:FiscalSign>" + fiscalSign + "</tns:FiscalSign>\n" +
                "               </tns:GetTicketInfo>\n" +
                "            </tns:GetTicketRequest>\n" +
                "         </ns0:Message>\n" +
                "      </ns0:SendMessageRequest>\n" +
                "   </soap-env:Body>\n" +
                "</soap-env:Envelope>\n", "POST", this.tempToken, "urn:SendMessageRequest");

        return response;
    }
}
