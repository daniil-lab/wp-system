package com.wp.system.request.auth;

import com.wp.system.utils.ValidationErrorMessages;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class EmailAuthRequest {
    @Schema(required = true, description = "Электронный адрес")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = ValidationErrorMessages.EMAIL_VALIDATION_FAILED)
    @NotEmpty(message = ValidationErrorMessages.NO_EMPTY)
    private String email;

    @Schema(required = true, description = "Пароль, закодированный в Base64")
    @NotEmpty(message = ValidationErrorMessages.NO_EMPTY)
    @Pattern(regexp = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$")
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
