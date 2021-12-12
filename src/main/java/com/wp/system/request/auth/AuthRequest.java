package com.wp.system.request.auth;

import com.wp.system.other.ValidationErrorMessages;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AuthRequest {
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    @Pattern(regexp = "^((\\+7|7|8)+([0-9]){10})$", message = ValidationErrorMessages.PHONE_VALIDATION_FAILED)
    private String username;

    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private String password;

    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
