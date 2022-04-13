package com.wp.system.utils.acquiring.tinkoff;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckPaymentRequest {
    @JsonProperty("TerminalKey")
    private String terminalKey;

    @JsonProperty("PaymentId")
    private Long paymentId;

    @JsonProperty("Token")
    private String token;

    public CheckPaymentRequest() {}

    public String getTerminalKey() {
        return terminalKey;
    }

    public void setTerminalKey(String terminalKey) {
        this.terminalKey = terminalKey;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
