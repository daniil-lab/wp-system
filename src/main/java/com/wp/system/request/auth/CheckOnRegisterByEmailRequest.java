package com.wp.system.request.auth;

import javax.validation.constraints.NotNull;

public class CheckOnRegisterByEmailRequest {
    @NotNull
    private String email;

    public CheckOnRegisterByEmailRequest() {}

    public CheckOnRegisterByEmailRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
