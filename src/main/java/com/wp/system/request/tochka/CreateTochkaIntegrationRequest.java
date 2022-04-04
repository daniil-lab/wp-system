package com.wp.system.request.tochka;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public class CreateTochkaIntegrationRequest {
    @NotNull
    private UUID userId;

    @NotNull
    private Instant startDate;

    @NotNull
    private String code;

    public CreateTochkaIntegrationRequest() {}

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
