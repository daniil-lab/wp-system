package com.wp.system.utils.fns.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class AuthResponse {
    @JacksonXmlProperty(namespace = "ns2", localName = "Token")
    private String Token;

    public AuthResponse() {}

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
