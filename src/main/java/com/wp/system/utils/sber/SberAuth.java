package com.wp.system.utils.sber;

import com.wp.system.entity.sber.SberIntegration;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.sber.SberIntegrationRepository;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
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
import java.util.List;

public class SberAuth {
    private final RestTemplate restTemplate = new RestTemplate();

    private SberIntegrationRepository sberIntegrationRepository;

    private SberIntegration sberIntegration;

    public SberAuth(SberIntegrationRepository sberIntegrationRepository, SberIntegration sberIntegration) {
        this.sberIntegrationRepository = sberIntegrationRepository;
        this.sberIntegration = sberIntegration;
    }

    public void auth() {
        preAuth();
        submitAuth();
    }

    private void preAuth() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> preAuthRequestBody = new LinkedMultiValueMap<String, String>();
        preAuthRequestBody.add("operation", "button.login");
        preAuthRequestBody.add("password", "17856");
        preAuthRequestBody.add("mGUID", sberIntegration.getmGUID());
        preAuthRequestBody.add("appType", "android");
        preAuthRequestBody.add("version", "9.20");
        preAuthRequestBody.add("appVersion", "12.15.0");
        preAuthRequestBody.add("osVersion", "28.0");
        preAuthRequestBody.add("isLightScheme", "false");
        preAuthRequestBody.add("isSafe", "true");
        preAuthRequestBody.add("deviceName", "HUAWEI_ANE-LX1");
        preAuthRequestBody.add("devId", "607d725604d1f032e50bb3c0622e791d3f400000");

        HttpEntity<MultiValueMap<String, String>> preAuthRequest = new HttpEntity<MultiValueMap<String, String>>(preAuthRequestBody, headers);

        ResponseEntity<String> preAuthResponse = restTemplate.postForEntity( "https://online.sberbank.ru:4477/CSAMAPI/login.do", preAuthRequest , String.class);

        System.out.println(preAuthResponse.getBody());

        String sessionCookie = SberUtils.exportSessionCookieFromCookies(preAuthResponse.getHeaders());

        if(sessionCookie == null)
            throw new ServiceException("Can`t exact session", HttpStatus.INTERNAL_SERVER_ERROR);

        sberIntegration.setSession(sessionCookie);

        Integer responseCode = SberUtils.getCodeFromResponse(preAuthResponse.getBody());

        if(responseCode == null || responseCode != 0)
            throw new ServiceException("Invalid SBER response code", HttpStatus.BAD_REQUEST);

        String token = SberUtils.getTokenFromResponse(preAuthResponse.getBody());

        if(token == null)
            throw new ServiceException("Can`t find SBER token", HttpStatus.INTERNAL_SERVER_ERROR);

        String host = SberUtils.getHostFromResponse(preAuthResponse.getBody());

        if(host == null)
            throw new ServiceException("Can`t find SBER host", HttpStatus.INTERNAL_SERVER_ERROR);

        sberIntegration.setHost(host);

        sberIntegration.setToken(token);

        sberIntegrationRepository.save(sberIntegration);
    }

    private void submitAuth() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> submitAuthRequestBody = new LinkedMultiValueMap<String, String>();
        submitAuthRequestBody.add("token", sberIntegration.getToken());
        submitAuthRequestBody.add("appName", "��������");
        submitAuthRequestBody.add("appBuildOSType", "android");
        submitAuthRequestBody.add("appVersion", "12.15.0");
        submitAuthRequestBody.add("appBuildType", "RELEASE");
        submitAuthRequestBody.add("appFormat", "STANDALONE");
        submitAuthRequestBody.add("deviceName", "HUAWEI_ANE-LX1");
        submitAuthRequestBody.add("deviceType", "ANE-LX1");
        submitAuthRequestBody.add("deviceOSType", "android");
        submitAuthRequestBody.add("deviceOSVersion", "9");

        HttpEntity<MultiValueMap<String, String>> submitAuthRequest = new HttpEntity<MultiValueMap<String, String>>(submitAuthRequestBody, headers);

        ResponseEntity<String> submitAuthResponse = restTemplate.postForEntity( "https://" + sberIntegration.getHost() + "/mobile9/postCSALogin.do", submitAuthRequest , String.class);

        System.out.println(submitAuthResponse.getBody());

        Integer responseCode = SberUtils.getCodeFromResponse(submitAuthResponse.getBody());

        if(responseCode == null || responseCode != 0)
            throw new ServiceException("Invalid SBER response code", HttpStatus.BAD_REQUEST);

        String sessionCookie = SberUtils.exportSessionCookieFromCookies(submitAuthResponse.getHeaders());

        if(sessionCookie == null)
            throw new ServiceException("Can`t exact session", HttpStatus.INTERNAL_SERVER_ERROR);

        List<String> responseCookies = submitAuthResponse.getHeaders().get(HttpHeaders.SET_COOKIE);

        StringBuilder cookieResult = new StringBuilder();

        for (String c : responseCookies)
            cookieResult.append(c.replaceAll("secure", "")).append(";");

        sberIntegration.setSession(cookieResult.toString());

        sberIntegrationRepository.save(sberIntegration);
    }

    public SberIntegrationRepository getSberIntegrationRepository() {
        return sberIntegrationRepository;
    }

    public void setSberIntegrationRepository(SberIntegrationRepository sberIntegrationRepository) {
        this.sberIntegrationRepository = sberIntegrationRepository;
    }

    public SberIntegration getSberIntegration() {
        return sberIntegration;
    }

    public void setSberIntegration(SberIntegration sberIntegration) {
        this.sberIntegration = sberIntegration;
    }
}
