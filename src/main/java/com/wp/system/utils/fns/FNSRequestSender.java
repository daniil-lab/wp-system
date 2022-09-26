package com.wp.system.utils.fns;

import com.wp.system.exception.ServiceException;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public final class FNSRequestSender {

    public static String send(String path, String content, String method, String tempToken, String action) {
        try {
            System.out.println(content);
            String url = "https://openapi.nalog.ru:8090/" + path;

            URL obj = new URL(url);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            if(tempToken != null) {
                con.setRequestProperty("FNS-OpenApi-Token", tempToken);
                con.setRequestProperty("FNS-OpenApi-UserToken", Base64.getEncoder().encodeToString("+7-926-527-77-54".getBytes()));
            }

            con.setRequestMethod(method);
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type","text/xml; charset=utf-8");

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());

            wr.writeBytes(content);

            wr.flush();
            wr.close();

            BufferedReader in = null;

            try {
                in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
            } catch (Exception e) {
                in = new BufferedReader(new InputStreamReader(
                        con.getErrorStream()));
            }

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
            throw new ServiceException("Error on send FNS request", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
