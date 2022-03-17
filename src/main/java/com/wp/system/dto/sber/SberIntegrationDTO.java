package com.wp.system.dto.sber;

import com.wp.system.entity.sber.SberIntegration;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

public class SberIntegrationDTO {
    private UUID id;

    private Instant startDate;

    public SberIntegrationDTO() {}

    public SberIntegrationDTO(SberIntegration sberIntegration) {
        if(sberIntegration == null)
            return;

        this.id = sberIntegration.getId();
        this.startDate = sberIntegration.getStartDate();
    }
}
