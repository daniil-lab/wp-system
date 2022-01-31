package com.wp.system.request.auth;

import javax.validation.constraints.NotNull;

public class CheckOnRegisterByPhoneRequest {
    @NotNull
    private String phone;

    public CheckOnRegisterByPhoneRequest() {}

    public CheckOnRegisterByPhoneRequest(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
