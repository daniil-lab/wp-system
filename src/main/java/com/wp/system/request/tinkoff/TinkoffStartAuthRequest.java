package com.wp.system.request.tinkoff;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class TinkoffStartAuthRequest {
    private String phone;

    private UUID userId;

    @NotNull
    private boolean reAuth;

    public TinkoffStartAuthRequest() {}

    public boolean isReAuth() {
        return reAuth;
    }

    public void setReAuth(boolean reAuth) {
        this.reAuth = reAuth;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
