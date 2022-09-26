package com.wp.system.response.wallet;

public class WalletCourseResponse {

    private String wallet;

    private Double value;

    public WalletCourseResponse() {};

    public WalletCourseResponse(String wallet, Double value) {
        this.wallet = wallet;
        this.value = value;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
