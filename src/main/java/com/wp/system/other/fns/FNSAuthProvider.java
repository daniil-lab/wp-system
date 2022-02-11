package com.wp.system.other.fns;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.wp.system.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
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

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);

            Document doc = dbf.newDocumentBuilder().parse(con.getInputStream());

            XPath xPath = XPathFactory.newInstance().newXPath();

            xPath.setNamespaceContext(new NamespaceContext() {
                @Override
                public String getNamespaceURI(String prefix) {
                    if(prefix.equals("ns"))
                        return "urn://x-artefacts-gnivc-ru/ais3/kkt/AuthService/types/1.0";

                    return null;
                }

                @Override
                public String getPrefix(String namespaceURI) {
                    return null;
                }

                @Override
                public Iterator<String> getPrefixes(String namespaceURI) {
                    return null;
                }
            });

            Node token = (Node) xPath.evaluate("/soap:Envelope/soap:Body/GetMessageResponse/Message/AuthResponse/ns2:Result/ns2:Token", doc, XPathConstants.NODE);

            System.out.println(token.getNodeValue());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("Error on get FNS auth", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
