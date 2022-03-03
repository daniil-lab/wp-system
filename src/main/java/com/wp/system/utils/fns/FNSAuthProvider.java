package com.wp.system.utils.fns;

import com.wp.system.exception.ServiceException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;

public class FNSAuthProvider {
    private String token;

    public FNSAuthProvider(String token) {
        this.token = token;
    }

    public String auth() {
        try {
            String response = FNSRequestSender.send("open-api/AuthService/0.1", "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-gnivc-ru/inplat/servin/OpenApiMessageConsumerService/types/1.0\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <ns:GetMessageRequest>\n" +
                    "         <ns:Message>\n" +
                    "            <tns:AuthRequest xmlns:tns=\"urn://x-artefacts-gnivc-ru/ais3/kkt/AuthService/types/1.0\">\n" +
                    "               <tns:AuthAppInfo>\n" +
                    "               <tns:MasterToken>" + this.token + "</tns:MasterToken>\n" +
                    "               </tns:AuthAppInfo>\n" +
                    "            </tns:AuthRequest>\n" +
                    "         </ns:Message>\n" +
                    "      </ns:GetMessageRequest>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>", "POST", null, "urn:GetMessageRequest");

            Document document = Jsoup.parse(response, "", Parser.xmlParser());
            document.outputSettings().prettyPrint(false);
            Elements retorno = document.getElementsByTag("ns2:Token");

            return retorno.get(0).text();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("Error on get FNS auth", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
