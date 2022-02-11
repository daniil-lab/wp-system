package com.wp.system.other.fns;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.wp.system.exception.ServiceException;
import com.wp.system.other.fns.response.AuthResponse;
import org.springframework.http.HttpStatus;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class FNSAuthProvider {
    private String token;

    public FNSAuthProvider(String token) {
        this.token = token;
    }

    public void auth() {
        try {
            String url = "https://openapi.nalog.ru:8090/open-api/AuthService/0.1";

            URL obj = new URL(url);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");

            con.setRequestProperty("Content-Type","text/xml; charset=utf-8");
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-gnivc-ru/inplat/servin/OpenApiMessageConsumerService/types/1.0\">\n" +
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
                    "</soapenv:Envelope>");
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));

            String inputLine;

            StringBuilder response = new StringBuilder();

            while((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            // You can play with response which is available as string now:
            String finalValue = response.toString();

            XmlMapper mapper = new XmlMapper();

            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            AuthResponse authResponse = mapper.readValue(finalValue, AuthResponse.class);

            System.out.println(authResponse.getToken());

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("Error on get FNS auth", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
