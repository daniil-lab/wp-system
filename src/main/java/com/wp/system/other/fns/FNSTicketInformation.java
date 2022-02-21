package com.wp.system.other.fns;

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
        String response = FNSRequestSender.send("open-api/ais3/KktService/0.1", "<soap-env:Envelope xmlns:soap-env=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "\t<soap-env:Body>\n" +
                "\t\t<ns0:SendMessageRequest xmlns:ns0=\"urn://x-artefacts-gnivc-ru/inplat/servin/OpenApiAsyncMessageConsumerService/types/1.0\">\n" +
                "\t\t\t<ns0:Message>\n" +
                "\t\t\t\t<tns:GetTicketRequest xmlns:tns=\"urn://x-artefacts-gnivc-ru/ais3/kkt/KktTicketService/types/1.0\">\n" +
                "\t<tns:GetTicketInfo>\n" +
                "\t\t<tns:Sum>" + sum.toString() + "</tns:Sum>\n" +
                "\t\t<tns:Date>" + date + "</tns:Date>\n" +
                "\t\t<tns:Fn>" + fn + "</tns:Fn>\n" +
                "\t\t<tns:TypeOperation>" + operationType + "</tns:TypeOperation>\n" +
                "\t\t<tns:FiscalDocumentId>" + fiscalDocumentId + "</tns:FiscalDocumentId>\n" +
                "\t\t<tns:FiscalSign>" + fiscalSign + "</tns:FiscalSign>\n" +
                "\t\t<tns:RawData>" + rawData + "</tns:RawData>\n" +
                "\t\t</tns:GetTicketInfo>\n" +
                "\t</tns:GetTicketRequest>\n" +
                "\t\t\t</ns0:Message>\n" +
                "\t\t</ns0:SendMessageRequest>\n" +
                "\t</soap-env:Body>\n" +
                "</soap-env:Envelope>", "POST", this.tempToken, "urn:SendMessageRequest");

        System.out.println("<soap-env:Envelope xmlns:soap-env=\\\"http://schemas.xmlsoap.org/soap/envelope/\\\">\\n\" +\n" +
                "                \"\\t<soap-env:Body>\\n\" +\n" +
                "                \"\\t\\t<ns0:SendMessageRequest xmlns:ns0=\\\"urn://x-artefacts-gnivc-ru/inplat/servin/OpenApiAsyncMessageConsumerService/types/1.0\\\">\\n\" +\n" +
                "                \"\\t\\t\\t<ns0:Message>\\n\" +\n" +
                "                \"\\t\\t\\t\\t<tns:GetTicketRequest xmlns:tns=\\\"urn://x-artefacts-gnivc-ru/ais3/kkt/KktTicketService/types/1.0\\\">\\n\" +\n" +
                "                \"\\t<tns:GetTicketInfo>\\n\" +\n" +
                "                \"\\t<tns:Sum>" + sum.toString() + "</tns:Sum>\\n\" +\n" +
                "                \"\\t\\t<tns:Date>" + date + "</tns:Date>\\n\" +\n" +
                "                \"\\t\\t<tns:Fn>" + fn + "</tns:Fn>\\n\" +\n" +
                "                \"\\t\\t<tns:TypeOperation>" + operationType + "</tns:TypeOperation>\\n\" +\n" +
                "                \"\\t\\t<tns:FiscalDocumentId>" + fiscalDocumentId + "</tns:FiscalDocumentId>\\n\" +\n" +
                "                \"\\t\\t<tns:FiscalSign>" + fiscalSign + "</tns:FiscalSign>\\n\" +\n" +
                "                \"\\t\\t<tns:RawData>" + rawData + "</tns:RawData>\\n\" +\n" +
                "                \"\\t\\t</tns:GetTicketInfo>\\n\" +\n" +
                "                \"\\t</tns:GetTicketRequest>\\n\" +\n" +
                "                \"\\t\\t\\t</ns0:Message>\\n\" +\n" +
                "                \"\\t\\t</ns0:SendMessageRequest>\\n\" +\n" +
                "                \"\\t</soap-env:Body>\\n\" +\n" +
                "                \"</soap-env:Envelope>");

        return response;
    }
}
