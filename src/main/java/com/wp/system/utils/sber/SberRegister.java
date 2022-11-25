package com.wp.system.utils.sber;

import com.wp.system.exception.ServiceException;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class SberRegister {
    private UUID userId;

    private String phone;

    private final RestTemplate restTemplate = new RestTemplate();

    private String jSession;

    private Instant startExportDate;

    private String mGUID;

    private final Instant createAt = Instant.now().plus(10, ChronoUnit.MINUTES);

    private SberRegisterState state = SberRegisterState.WAIT_REGISTER;

    private String token;

    private String host;

    public SberRegister(UUID userId, String phone) {
        this.userId = userId;
        this.phone = phone;
    }

    public void register() {
        try {
            if(!state.equals(SberRegisterState.WAIT_REGISTER))
                throw new ServiceException("Try recreate sber register instance", HttpStatus.INTERNAL_SERVER_ERROR);

            state = SberRegisterState.REGISTER;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
            map.add("operation", "register");
            map.add("login", phone);
            map.add("appType", "android");
            map.add("version", "9.20");
            map.add("appVersion", "12.15.0");
            map.add("deviceName", "HUAWEI_ANE-LX1");
            map.add("devID", "607d725604d1f032e50bb3c0622e791d3f400000");
            map.add("devIdOld", "607d725604d1f032e50bb3c0622e791d3f400000");

            map.add("mobileSdkData", "{\\\"TIMESTAMP\\\":\\\"2019-09-13T07:23:14Z\\\",\\\"HardwareID\\\":\\\"-1\\\",\\\"SIM_ID\\\":\\\"-1\\\",\\\"PhoneNumber\\\":\\\"-1\\\",\\\"GeoLocationInfo\\\":[{\\\"Timestamp\\\":\\\"0\\\",\\\"Status\\\":\\\"1\\\"}],\\\"DeviceModel\\\":\\\"ANE-LX1\\\",\\\"MultitaskingSupported\\\":true,\\\"DeviceName\\\":\\\"marky\\\",\\\"DeviceSystemName\\\":\\\"Android\\\",\\\"DeviceSystemVersion\\\":\\\"28\\\",\\\"Languages\\\":\\\"ru\\\",\\\"WiFiMacAddress\\\":\\\"02:00:00:00:00:00\\\",\\\"WiFiNetworksData\\\":{\\\"BBSID\\\":\\\"02:00:00:00:00:00\\\",\\\"SignalStrength\\\":\\\"-47\\\",\\\"Channel\\\":\\\"null\\\"},\\\"CellTowerId\\\":\\\"-1\\\",\\\"LocationAreaCode\\\":\\\"-1\\\",\\\"ScreenSize\\\":\\\"1080x2060\\\",\\\"RSA_ApplicationKey\\\":\\\"2C501591EA5BF79F1C0ABA8B628C2571\\\",\\\"MCC\\\":\\\"286\\\",\\\"MNC\\\":\\\"02\\\",\\\"OS_ID\\\":\\\"1f32651b72df5515\\\",\\\"SDK_VERSION\\\":\\\"3.10.0\\\",\\\"Compromised\\\":0,\\\"Emulator\\\":0}");
            map.add("mobileSDKKAV", "{\\\"osVersion\\\":0,\\\"KavSdkId\\\":\\\"\\\",\\\"KavSdkVersion\\\":\\\"\\\",\\\"KavSdkVirusDBVersion\\\":\\\"SdkVirusDbInfo(year=0, month=0, day=0, hour=0, minute=0, second=0, knownThreatsCount=0, records=0, size=0)\\\",\\\"KavSdkVirusDBStatus\\\":\\\"\\\",\\\"KavSdkVirusDBStatusDate\\\":\\\"\\\",\\\"KavSdkRoot\\\":false,\\\"LowPasswordQuality\\\":false,\\\"NonMarketAppsAllowed\\\":false,\\\"UsbDebugOn\\\":false,\\\"ScanStatus\\\":\\\"NONE\\\"}");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

            ResponseEntity<String> response = restTemplate.postForEntity( "https://online.sberbank.ru:4477/CSAMAPI/registerApp.do", request , String.class);

            Integer responseCode = SberUtils.getCodeFromResponse(response.getBody());

            if(responseCode == null || responseCode != 0) {
                throw new ServiceException("Invalid response code from SBER", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String responseMGUID = SberUtils.getMGUIDFromResponse(response.getBody());

            if(responseMGUID == null)
                throw new ServiceException("Invalid MGUID", HttpStatus.INTERNAL_SERVER_ERROR);

            mGUID = responseMGUID;

            String sessionCookie = SberUtils.exportSessionCookieFromCookies(response.getHeaders());

            if(sessionCookie == null)
                throw new ServiceException("Can`t exact session", HttpStatus.INTERNAL_SERVER_ERROR);

            jSession = sessionCookie;

            state = SberRegisterState.WAIT_SUBMIT;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void submitRegister(String code) {
        try {
            if(!state.equals(SberRegisterState.WAIT_SUBMIT))
                throw new ServiceException("Try recreate sber register instance", HttpStatus.INTERNAL_SERVER_ERROR);

            state = SberRegisterState.SUBMIT;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.set("Set-Cookie", jSession);

            MultiValueMap<String, String> confirmRequestBody = new LinkedMultiValueMap<String, String>();
            confirmRequestBody.add("operation", "confirm");
            confirmRequestBody.add("confirmData", code);
            confirmRequestBody.add("smsPassword", code);
            confirmRequestBody.add("mGUID", mGUID);
            confirmRequestBody.add("appType", "android");
            confirmRequestBody.add("version", "9.20");
            confirmRequestBody.add("appVersion", "12.15.0");
            confirmRequestBody.add("deviceName", "HUAWEI_ANE-LX1");
            confirmRequestBody.add("devID", "607d725604d1f032e50bb3c0622e791d3f400000");
            confirmRequestBody.add("devIdOld", "607d725604d1f032e50bb3c0622e791d3f400000");

            confirmRequestBody.add("mobileSdkData", "{\\\"TIMESTAMP\\\":\\\"2019-09-13T07:23:14Z\\\",\\\"HardwareID\\\":\\\"-1\\\",\\\"SIM_ID\\\":\\\"-1\\\",\\\"PhoneNumber\\\":\\\"-1\\\",\\\"GeoLocationInfo\\\":[{\\\"Timestamp\\\":\\\"0\\\",\\\"Status\\\":\\\"1\\\"}],\\\"DeviceModel\\\":\\\"ANE-LX1\\\",\\\"MultitaskingSupported\\\":true,\\\"DeviceName\\\":\\\"marky\\\",\\\"DeviceSystemName\\\":\\\"Android\\\",\\\"DeviceSystemVersion\\\":\\\"28\\\",\\\"Languages\\\":\\\"ru\\\",\\\"WiFiMacAddress\\\":\\\"02:00:00:00:00:00\\\",\\\"WiFiNetworksData\\\":{\\\"BBSID\\\":\\\"02:00:00:00:00:00\\\",\\\"SignalStrength\\\":\\\"-47\\\",\\\"Channel\\\":\\\"null\\\"},\\\"CellTowerId\\\":\\\"-1\\\",\\\"LocationAreaCode\\\":\\\"-1\\\",\\\"ScreenSize\\\":\\\"1080x2060\\\",\\\"RSA_ApplicationKey\\\":\\\"2C501591EA5BF79F1C0ABA8B628C2571\\\",\\\"MCC\\\":\\\"286\\\",\\\"MNC\\\":\\\"02\\\",\\\"OS_ID\\\":\\\"1f32651b72df5515\\\",\\\"SDK_VERSION\\\":\\\"3.10.0\\\",\\\"Compromised\\\":0,\\\"Emulator\\\":0}");
            confirmRequestBody.add("mobileSDKKAV", "{\\\"osVersion\\\":0,\\\"KavSdkId\\\":\\\"\\\",\\\"KavSdkVersion\\\":\\\"\\\",\\\"KavSdkVirusDBVersion\\\":\\\"SdkVirusDbInfo(year=0, month=0, day=0, hour=0, minute=0, second=0, knownThreatsCount=0, records=0, size=0)\\\",\\\"KavSdkVirusDBStatus\\\":\\\"\\\",\\\"KavSdkVirusDBStatusDate\\\":\\\"\\\",\\\"KavSdkRoot\\\":false,\\\"LowPasswordQuality\\\":false,\\\"NonMarketAppsAllowed\\\":false,\\\"UsbDebugOn\\\":false,\\\"ScanStatus\\\":\\\"NONE\\\"}");

            HttpEntity<MultiValueMap<String, String>> confirmRequest = new HttpEntity<MultiValueMap<String, String>>(confirmRequestBody, headers);

            ResponseEntity<String> confirmResponse = restTemplate.postForEntity( "https://online.sberbank.ru:4477/CSAMAPI/registerApp.do", confirmRequest , String.class);

            System.out.println(confirmResponse.getBody());

            Integer responseCode = SberUtils.getCodeFromResponse(confirmResponse.getBody());

            if(responseCode == null || responseCode != 0) {
                throw new ServiceException("Invalid response code from SBER", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            state = SberRegisterState.WAIT_CREATE_PIN;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createPin() {
        if(!state.equals(SberRegisterState.WAIT_CREATE_PIN))
            throw new ServiceException("Try recreate sber register instance", HttpStatus.INTERNAL_SERVER_ERROR);

        state = SberRegisterState.CREATE_PIN;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Set-Cookie", jSession);

        MultiValueMap<String, String> createPinRequestBody = new LinkedMultiValueMap<String, String>();
        createPinRequestBody.add("operation", "createPIN");
        createPinRequestBody.add("password", "17856");
        createPinRequestBody.add("mGUID", mGUID);
        createPinRequestBody.add("appType", "android");
        createPinRequestBody.add("version", "9.20");
        createPinRequestBody.add("appVersion", "12.15.0");
        createPinRequestBody.add("deviceName", "HUAWEI_ANE-LX1");
        createPinRequestBody.add("devID", "607d725604d1f032e50bb3c0622e791d3f400000");
        createPinRequestBody.add("devIdOld", "607d725604d1f032e50bb3c0622e791d3f400000");

        createPinRequestBody.add("mobileSdkData", "{\\\"TIMESTAMP\\\":\\\"2019-09-13T07:23:14Z\\\",\\\"HardwareID\\\":\\\"-1\\\",\\\"SIM_ID\\\":\\\"-1\\\",\\\"PhoneNumber\\\":\\\"-1\\\",\\\"GeoLocationInfo\\\":[{\\\"Timestamp\\\":\\\"0\\\",\\\"Status\\\":\\\"1\\\"}],\\\"DeviceModel\\\":\\\"ANE-LX1\\\",\\\"MultitaskingSupported\\\":true,\\\"DeviceName\\\":\\\"marky\\\",\\\"DeviceSystemName\\\":\\\"Android\\\",\\\"DeviceSystemVersion\\\":\\\"28\\\",\\\"Languages\\\":\\\"ru\\\",\\\"WiFiMacAddress\\\":\\\"02:00:00:00:00:00\\\",\\\"WiFiNetworksData\\\":{\\\"BBSID\\\":\\\"02:00:00:00:00:00\\\",\\\"SignalStrength\\\":\\\"-47\\\",\\\"Channel\\\":\\\"null\\\"},\\\"CellTowerId\\\":\\\"-1\\\",\\\"LocationAreaCode\\\":\\\"-1\\\",\\\"ScreenSize\\\":\\\"1080x2060\\\",\\\"RSA_ApplicationKey\\\":\\\"2C501591EA5BF79F1C0ABA8B628C2571\\\",\\\"MCC\\\":\\\"286\\\",\\\"MNC\\\":\\\"02\\\",\\\"OS_ID\\\":\\\"1f32651b72df5515\\\",\\\"SDK_VERSION\\\":\\\"3.10.0\\\",\\\"Compromised\\\":0,\\\"Emulator\\\":0}");
        createPinRequestBody.add("mobileSDKKAV", "{\\\"osVersion\\\":0,\\\"KavSdkId\\\":\\\"\\\",\\\"KavSdkVersion\\\":\\\"\\\",\\\"KavSdkVirusDBVersion\\\":\\\"SdkVirusDbInfo(year=0, month=0, day=0, hour=0, minute=0, second=0, knownThreatsCount=0, records=0, size=0)\\\",\\\"KavSdkVirusDBStatus\\\":\\\"\\\",\\\"KavSdkVirusDBStatusDate\\\":\\\"\\\",\\\"KavSdkRoot\\\":false,\\\"LowPasswordQuality\\\":false,\\\"NonMarketAppsAllowed\\\":false,\\\"UsbDebugOn\\\":false,\\\"ScanStatus\\\":\\\"NONE\\\"}");

        HttpEntity<MultiValueMap<String, String>> createPinRequest = new HttpEntity<MultiValueMap<String, String>>(createPinRequestBody, headers);

        ResponseEntity<String> createPinResponse = restTemplate.postForEntity( "https://online.sberbank.ru:4477/CSAMAPI/registerApp.do", createPinRequest , String.class);

        Integer responseCode = SberUtils.getCodeFromResponse(createPinResponse.getBody());

        if(responseCode == null || responseCode != 0) {
            throw new ServiceException("Invalid response code from SBER", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String responseToken = SberUtils.getTokenFromResponse(createPinResponse.getBody());
        String responseHost = SberUtils.getHostFromResponse(createPinResponse.getBody());

        if(responseToken == null)
            throw new ServiceException("SBER Token not found", HttpStatus.INTERNAL_SERVER_ERROR);

        if(responseHost == null)
            throw new ServiceException("SBER Host not found", HttpStatus.INTERNAL_SERVER_ERROR);

        token = responseToken;
        host = responseHost;

        state = SberRegisterState.FINISHED;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public Instant getStartExportDate() {
        return startExportDate;
    }

    public void setStartExportDate(Instant startExportDate) {
        this.startExportDate = startExportDate;
    }

    public String getjSession() {
        return jSession;
    }

    public String getmGUID() {
        return mGUID;
    }

    public String getToken() {
        return token;
    }

    public String getHost() {
        return host;
    }
}
