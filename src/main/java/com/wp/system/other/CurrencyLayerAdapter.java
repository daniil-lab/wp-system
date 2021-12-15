package com.wp.system.other;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriBuilder;

import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Map;

@Component
public class CurrencyLayerAdapter {
    private final static String LIVE_REST_URL = "http://apilayer.net/api/live";
    private final static String LIST_REST_URL = "http://apilayer.net/api/list";

    private final static String API_TOKEN = "7ad6a31786a1023c252632045b420f5b";

    public CurrencyLayerAdapter() {

    }

    public Map<String, Object> getAllCurses() {
        try {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URIBuilder(LIVE_REST_URL)
                            .setParameter("access_key", API_TOKEN)
                            .setParameter("source", WalletType.USD.name())
                            .build())
                    .build();

            HttpResponse<String> getResponse = HttpClient.newBuilder().build().send(getRequest, HttpResponse.BodyHandlers.ofString());

            return (Map<String, Object>) new ObjectMapper().readValue(getResponse.body(), new TypeReference<Map<String, Object>>() {}).get("quotes");
        } catch (Exception e) {
            return null;
        }
    }

    public void getWalletCurse(WalletType parentWallet, WalletType[] findWallet) {
        try {
            StringBuilder parsedWallets = new StringBuilder();

            for (int i = 0; i < findWallet.length ; i++) {
                if (i == findWallet.length - 1) {
                    parsedWallets.append(findWallet[i].name());
                } else {
                    parsedWallets.append(findWallet[i].name()).append(",");
                }
            }

            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URIBuilder(LIVE_REST_URL)
                            .setParameter("access_key", API_TOKEN)
                            .setParameter("source", parentWallet.name())
                            .setParameter("currencies", parsedWallets.toString())
                            .build())
                    .build();

            HttpResponse<String> getResponse = HttpClient.newBuilder().build().send(getRequest, HttpResponse.BodyHandlers.ofString());

            System.out.println(getResponse.body());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
