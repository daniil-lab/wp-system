package com.wp.system.other.fns;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;

public class FNSTicketInformation extends FNSIntegration {
    public FNSTicketInformation(String tempToken) {
        super(tempToken);
    }

    public String getTicketInformation(Double sum,
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
                "                  <tns:Sum>267448566</tns:Sum>\n" +
                "                  <tns:Date>" + "07.02.2018T10:08" + "</tns:Date>\n" +
                "                  <tns:Fn>" + "8710000100682017" + "</tns:Fn>\n" +
                "                  <tns:TypeOperation>" + "1" + "</tns:TypeOperation>\n" +
                "                  <tns:FiscalDocumentId>" + "0000176902" + "</tns:FiscalDocumentId>\n" +
                "                  <tns:FiscalSign>" + "1266035174" + "</tns:FiscalSign>\n" +
                "                  <tns:RawData>" + rawData + "</tns:RawData>\n" +
                "               </tns:GetTicketInfo>\n" +
                "            </tns:GetTicketRequest>\n" +
                "         </ns0:Message>\n" +
                "      </ns0:SendMessageRequest>\n" +
                "   </soap-env:Body>\n" +
                "</soap-env:Envelope>\n", "POST", this.tempToken, "urn:SendMessageRequest");

        return response;
    }
}
