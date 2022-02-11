package com.wp.system.other.fns;

public class FNSTicketInformation extends FNSIntegration {
    public FNSTicketInformation(String tempToken) {
        super(tempToken);
    }

    public String getTicketInformation(int sum,
                                     String date,
                                     String fn,
                                     int operationType,
                                     int fiscalDocumentId,
                                     int fiscalSign,
                                     boolean rawData) {
        String response = FNSRequestSender.send("open-api/ais3/KktService/0.1", "<soap-env:Envelope xmlns:soap-env=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "\t<soap-env:Body>\n" +
                "\t\t<ns0:SendMessageRequest xmlns:ns0=\"urn://x-artefacts-gnivc-ru/inplat/servin/OpenApiAsyncMessageConsumerService/types/1.0\">\n" +
                "\t\t\t<ns0:Message>\n" +
                "\t\t\t\t<tns:GetTicketRequest xmlns:tns=\"urn://x-artefacts-gnivc-ru/ais3/kkt/KktTicketService/types/1.0\">\n" +
                "\t<tns:GetTicketInfo>\n" +
                "\t<tns:Sum>2400</tns:Sum>\n" +
                "\t\t<tns:Date>2019-04-09T16:38:00</tns:Date>\n" +
                "\t\t<tns:Fn>9287440300090728</tns:Fn>\n" +
                "\t\t<tns:TypeOperation>1</tns:TypeOperation>\n" +
                "\t\t<tns:FiscalDocumentId>77133</tns:FiscalDocumentId>\n" +
                "\t\t<tns:FiscalSign>1482926127</tns:FiscalSign>\n" +
                "\t\t<tns:RawData>true</tns:RawData>\n" +
                "\t\t</tns:GetTicketInfo>\n" +
                "\t</tns:GetTicketRequest>\n" +
                "\t\t\t</ns0:Message>\n" +
                "\t\t</ns0:SendMessageRequest>\n" +
                "\t</soap-env:Body>\n" +
                "</soap-env:Envelope>", "POST", this.tempToken);

        return response;
    }
}
