package com.wp.system.request.tinkoff;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class TinkoffStartAuthRequest {
    @NotNull
    private String phone;

    @NotNull
    private UUID userId;

    public TinkoffStartAuthRequest() {}

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
