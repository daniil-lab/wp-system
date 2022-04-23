package com.wp.system.utils.tinkoff.request;

public class TinkoffSmsRequest {
    private String phone;

    private String password;

    public TinkoffSmsRequest() {}

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
