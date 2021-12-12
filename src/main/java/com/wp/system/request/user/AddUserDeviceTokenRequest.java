package com.wp.system.request.user;

import com.wp.system.other.ValidationErrorMessages;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class AddUserDeviceTokenRequest {
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private UUID userId;

    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private String token;

    public AddUserDeviceTokenRequest() {};

    public AddUserDeviceTokenRequest(UUID userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
