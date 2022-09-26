package com.wp.system.request.loyalty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class UpdateLoyaltyCardRequest {
    private UUID blankId;

    private UUID userId;

    private String data;

    public UpdateLoyaltyCardRequest() {}

    public UpdateLoyaltyCardRequest(UUID blankId, UUID userId, String data) {
        this.blankId = blankId;
        this.userId = userId;
        this.data = data;
    }

    public UUID getBlankId() {
        return blankId;
    }

    public void setBlankId(UUID blankId) {
        this.blankId = blankId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
