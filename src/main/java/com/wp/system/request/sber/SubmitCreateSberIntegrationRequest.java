package com.wp.system.request.sber;

import java.util.UUID;

public class SubmitCreateSberIntegrationRequest {
    private UUID userId;

    private String code;

    public SubmitCreateSberIntegrationRequest() {}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
