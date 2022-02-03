package com.wp.system.request.subscription;

import java.util.UUID;

public class UseVariantOnUserRequest {
    private UUID userId;

    private UUID variantId;

    public UseVariantOnUserRequest() {}

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getVariantId() {
        return variantId;
    }

    public void setVariantId(UUID variantId) {
        this.variantId = variantId;
    }
}
