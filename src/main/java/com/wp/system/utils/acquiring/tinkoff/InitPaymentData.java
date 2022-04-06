package com.wp.system.utils.acquiring.tinkoff;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class InitPaymentData {
    @JsonProperty("UserId")
    private UUID userId;

    @JsonProperty("SubVariantId")
    private UUID subVariantId;

    public InitPaymentData() {}

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getSubVariantId() {
        return subVariantId;
    }

    public void setSubVariantId(UUID subVariantId) {
        this.subVariantId = subVariantId;
    }
}
