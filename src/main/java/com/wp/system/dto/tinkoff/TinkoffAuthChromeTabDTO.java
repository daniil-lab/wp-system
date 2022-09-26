package com.wp.system.dto.tinkoff;

import com.wp.system.entity.tinkoff.TinkoffIntegration;
import com.wp.system.entity.tinkoff.TinkoffSyncStage;
import com.wp.system.utils.tinkoff.TinkoffAuthChromeTab;

import java.time.Instant;
import java.util.UUID;

public class TinkoffAuthChromeTabDTO {
    private UUID id;

    private UUID userId;

    private Instant expiredAt;

    public TinkoffAuthChromeTabDTO() {}

    public TinkoffAuthChromeTabDTO(TinkoffAuthChromeTab tab) {
        if(tab == null)
            return;

        this.id = tab.getId();
        this.userId = tab.getUserId();
        this.expiredAt = tab.getExpiredAt();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Instant getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Instant expiredAt) {
        this.expiredAt = expiredAt;
    }
}
