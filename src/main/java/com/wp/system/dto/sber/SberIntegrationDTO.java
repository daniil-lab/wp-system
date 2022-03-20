package com.wp.system.dto.sber;

import com.wp.system.entity.sber.SberIntegration;
import com.wp.system.entity.sber.SberIntegrationState;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

public class SberIntegrationDTO {
    private UUID id;

    private Instant startDate;

    private SberIntegrationState state;

    public SberIntegrationDTO() {}

    public SberIntegrationDTO(SberIntegration sberIntegration) {
        if(sberIntegration == null)
            return;

        this.state = sberIntegration.getState();
        this.id = sberIntegration.getId();
        this.startDate = sberIntegration.getStartDate();
    }

    public SberIntegrationState getState() {
        return state;
    }

    public void setState(SberIntegrationState state) {
        this.state = state;
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
}
