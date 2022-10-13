package com.wp.system.dto;

import com.wp.system.entity.sber.SberIntegration;
import com.wp.system.entity.tinkoff.TinkoffIntegration;
import com.wp.system.utils.Bank;

import java.util.UUID;

public class AbstractIntegrationDTO {
    private UUID id;

    private Bank type;

    public AbstractIntegrationDTO(TinkoffIntegration tinkoffIntegration) {
        this.id = tinkoffIntegration.getId();
        this.type = Bank.TINKOFF;
    }

    public AbstractIntegrationDTO(SberIntegration sberIntegration) {
        this.id = sberIntegration.getId();
        this.type = Bank.SBER;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Bank getType() {
        return type;
    }

    public void setType(Bank type) {
        this.type = type;
    }
}
