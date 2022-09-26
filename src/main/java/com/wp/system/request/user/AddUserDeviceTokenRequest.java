package com.wp.system.request.user;

import com.wp.system.utils.ValidationErrorMessages;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class AddUserDeviceTokenRequest {
    @Schema(required = true, description = "Токен устройства")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private String token;

    public AddUserDeviceTokenRequest() {};

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
