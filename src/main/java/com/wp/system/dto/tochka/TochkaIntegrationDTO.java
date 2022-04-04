package com.wp.system.dto.tochka;

import com.wp.system.entity.tochka.TochkaIntegration;

import java.time.Instant;
import java.util.UUID;

public class TochkaIntegrationDTO {
    private UUID id;

    private Instant startDate;

    private Instant lastSyncDate;

    public TochkaIntegrationDTO() {}

    public TochkaIntegrationDTO(TochkaIntegration ti) {
        if(ti == null)
            return;

        this.id = ti.getId();
        this.startDate = ti.getStartDate();
        this.lastSyncDate = ti.getLastOperationsSyncDate();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getLastSyncDate() {
        return lastSyncDate;
    }

    public void setLastSyncDate(Instant lastSyncDate) {
        this.lastSyncDate = lastSyncDate;
    }
}
