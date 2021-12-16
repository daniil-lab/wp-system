package com.wp.system.response.auth;

public class CheckOnRegisterRequest {
    private String phone;

    public CheckOnRegisterRequest() {}

    public CheckOnRegisterRequest(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
