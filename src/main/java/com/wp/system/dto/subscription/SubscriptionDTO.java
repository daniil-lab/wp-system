package com.wp.system.dto.subscription;

import com.wp.system.entity.subscription.Subscription;

import java.time.Instant;
import java.util.UUID;

public class SubscriptionDTO {
    private UUID id;

    private boolean isActive;

    private Instant startDate;

    private Instant endDate;

    public SubscriptionDTO() {}

    public SubscriptionDTO(Subscription subscription) {
        this.id = subscription.getId();
        this.isActive = subscription.isActive();
        this.startDate = subscription.getStartDate();
        this.endDate = subscription.getEndDate();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }
}
