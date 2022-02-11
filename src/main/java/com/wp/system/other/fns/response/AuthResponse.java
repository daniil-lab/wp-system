package com.wp.system.other.fns.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class AuthResponse {
    @JacksonXmlProperty(namespace = "ns2", localName = "Result")
    private String Result;

    public AuthResponse() {}

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }
}
