package com.wp.system.request.auth;

import com.wp.system.other.ValidationErrorMessages;

import javax.validation.constraints.NotEmpty;

public class EmailAuthRequest {
    @NotEmpty(message = ValidationErrorMessages.NO_EMPTY)
    private String email;

    @NotEmpty(message = ValidationErrorMessages.NO_EMPTY)
    private String password;

    public EmailAuthRequest() {}

    public EmailAuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
