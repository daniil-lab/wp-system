package com.wp.system.request.sber;

import java.util.UUID;

public class SubmitCreateSberIntegrationRequest {
    private UUID userId;

    private String code;

    private String sberMobilePassword;

    public SubmitCreateSberIntegrationRequest() {}

    public String getSberMobilePassword() {
        return sberMobilePassword;
    }

    public void setSberMobilePassword(String sberMobilePassword) {
        this.sberMobilePassword = sberMobilePassword;
    }

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
