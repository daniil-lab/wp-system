package com.wp.system.other.fns;

import com.wp.system.exception.ServiceException;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public final class FNSRequestSender {

    public static String send(String path, String content, String method, String tempToken) {
        try {
            String url = "https://openapi.nalog.ru:8090/" + path;

            URL obj = new URL(url);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod(method);
            con.setDoOutput(true);

            if(tempToken != null) {
                con.setRequestProperty("FNS-OpenApi-Token", tempToken);
                con.setRequestProperty("FNS-OpenApi-UserToken", Base64.getEncoder().encodeToString("+7-926-527-77-54".getBytes()));
            }

            con.setRequestProperty("Content-Type","text/xml; charset=utf-8");
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());

            wr.writeBytes(content);

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

            String finalValue = response.toString();

            return finalValue;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("Error on get FNS auth", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
