package com.wp.system.other;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriBuilder;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

@Component
public class CurrencyLayerAdapter {
    private final static String REST_URL = "http://apilayer.net/api/live";

    private final static String API_TOKEN = "7ad6a31786a1023c252632045b420f5b";

    public CurrencyLayerAdapter() {

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
                    .uri(new URIBuilder(REST_URL)
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
