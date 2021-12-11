package com.wp.system.request.auth;

import com.wp.system.other.ValidationErrorMessages;

import javax.validation.constraints.NotNull;

public class AuthRequest {
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private String username;

    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private String password;

    public AuthRequest() {};

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
