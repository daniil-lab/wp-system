package com.wp.system.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wp.system.exception.ServiceException;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class Geocoder {
    private static final String API = "https://nominatim.openstreetmap.org/reverse";

    public static String getPlaceByCoords(double lon, double lat) {
        try {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URIBuilder(API)
                            .setParameter("format", "jsonv2")
                            .setParameter("lon", String.valueOf(lon))
                            .setParameter("lat", String.valueOf(lat))
                            .build())
                    .setHeader("accept-language", "ru-RU")
                    .build();

            HttpResponse<String> getResponse = HttpClient.newBuilder().build().send(getRequest, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> data = mapper.readValue(getResponse.body(), new TypeReference<Map<String, Object>>() {});

            return (String) data.get("display_name");
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new ServiceException("Geocoder error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
