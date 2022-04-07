package com.wp.system.dto.tinkoff;

import com.wp.system.dto.category.CategoryDTO;
import com.wp.system.entity.tinkoff.TinkoffIntegration;
import com.wp.system.entity.tinkoff.TinkoffSyncStage;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

public class TinkoffIntegrationDTO {
    private UUID id;

    private Instant startDate;

    private Instant lastOperationsSyncDate;

    private TinkoffSyncStage stage;

    private CategoryDTO category;

    public TinkoffIntegrationDTO() {}

    public TinkoffIntegrationDTO(TinkoffIntegration ti) {
        if(ti == null)
            return;

        this.id = ti.getId();
        this.stage = ti.getStage();
        this.startDate = ti.getStartDate();
        this.lastOperationsSyncDate = ti.getLastOperationsSyncDate();
        this.category = new CategoryDTO(t.getCategory());
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
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

    public Instant getLastOperationsSyncDate() {
        return lastOperationsSyncDate;
    }

    public void setLastOperationsSyncDate(Instant lastOperationsSyncDate) {
        this.lastOperationsSyncDate = lastOperationsSyncDate;
    }

    public TinkoffSyncStage getStage() {
        return stage;
    }

    public void setStage(TinkoffSyncStage stage) {
        this.stage = stage;
    }
}
