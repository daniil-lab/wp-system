package com.wp.system.request.sber;

import java.time.Instant;
import java.util.UUID;

public class CreateSberIntegrationRequest {
    private UUID userId;

    private String phone;

    private Instant startExportDate;

    public CreateSberIntegrationRequest() {}

    public Instant getStartExportDate() {
        return startExportDate;
    }

    public void setStartExportDate(Instant startExportDate) {
        this.startExportDate = startExportDate;
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
