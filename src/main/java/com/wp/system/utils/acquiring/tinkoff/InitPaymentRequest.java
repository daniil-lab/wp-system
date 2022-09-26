package com.wp.system.utils.acquiring.tinkoff;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InitPaymentRequest {
    @JsonProperty("TerminalKey")
    private String TerminalKey;

    @JsonProperty("Amount")
    private Integer Amount;

//    @JsonProperty("SuccessURL")
//    private String SuccessURL;
//
//    @JsonProperty("FailURL")
//    private String FailURL;

    @JsonProperty("OrderId")
    private String OrderId;

    @JsonProperty("DATA")
    private InitPaymentData DATA;

    @JsonProperty("Receipt")
    private InitPaymentReceipt Receipt;

    public InitPaymentRequest() {}

//    public String getFailURL() {
//        return FailURL;
//    }
//
//    public void setFailURL(String failURL) {
//        FailURL = failURL;
//    }
//
//    public String getSuccessURL() {
//        return SuccessURL;
//    }
//
//    public void setSuccessURL(String successURL) {
//        SuccessURL = successURL;
//    }

    @Override
    public String toString() {
        return "InitPaymentRequest{" +
                "TerminalKey='" + TerminalKey + '\'' +
                ", Amount='" + Amount + '\'' +
                ", OrderId='" + OrderId + '\'' +
                ", DATA=" + DATA +
                ", Receipt=" + Receipt +
                '}';
    }

    public InitPaymentData getDATA() {
        return DATA;
    }

    public void setDATA(InitPaymentData DATA) {
        this.DATA = DATA;
    }

    public InitPaymentReceipt getReceipt() {
        return Receipt;
    }

    public void setReceipt(InitPaymentReceipt receipt) {
        Receipt = receipt;
    }

    public String getTerminalKey() {
        return TerminalKey;
    }

    public void setTerminalKey(String terminalKey) {
        TerminalKey = terminalKey;
    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }
}
