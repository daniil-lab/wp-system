package com.wp.system.utils.sber;

import org.springframework.http.HttpHeaders;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;

public final class SberUtils {
    public static Integer getCodeFromResponse(String response) {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(response)));

            XPath xpath = XPathFactory.newInstance().newXPath();

            XPathExpression expression = xpath.compile("/response/status/code/text()");

            Node node = (Node) expression.evaluate(doc, XPathConstants.NODE);

            return Integer.parseInt(node.getNodeValue());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getMGUIDFromResponse(String response) {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(response)));

            XPath xpath = XPathFactory.newInstance().newXPath();

            XPathExpression expression = xpath.compile("/response/confirmRegistrationStage/mGUID/text()");

            Node node = (Node) expression.evaluate(doc, XPathConstants.NODE);

            return node.getNodeValue();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getTokenFromResponse(String response) {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(response)));

            XPath xpath = XPathFactory.newInstance().newXPath();

            XPathExpression expression = xpath.compile("/response/loginData/token/text()");

            Node node = (Node) expression.evaluate(doc, XPathConstants.NODE);

            return node.getNodeValue();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String exportSessionCookieFromCookies(HttpHeaders headers) {
        for (String cookie : headers.get(HttpHeaders.SET_COOKIE)) {
            if(cookie.startsWith("JSESSIONID")) {
                System.out.println(cookie.split(";")[0]);
                return cookie.split(";")[0];
            }
        }

        return null;
    }

    public static String getHostFromResponse(String response) {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(response)));

            XPath xpath = XPathFactory.newInstance().newXPath();

            XPathExpression expression = xpath.compile("/response/loginData/host/text()");

            Node node = (Node) expression.evaluate(doc, XPathConstants.NODE);

            return node.getNodeValue();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getDataFromResponse(String response, String path) {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(response)));

            XPath xpath = XPathFactory.newInstance().newXPath();

            XPathExpression expression = xpath.compile(path);


            return expression.evaluate(doc, XPathConstants.NODE);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getListDataFromResponse(String response, String path) {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(response)));

            doc.normalizeDocument();

            return doc.getElementsByTagName(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
