package com.wp.system.entity.subscription;

import com.wp.system.entity.user.User;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
public class Subscription {
    @Id
    private UUID id = UUID.randomUUID();

    private boolean active;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant startDate;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant endDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "variant_id")
    private SubscriptionVariant variant;

    public Subscription() {}

    public SubscriptionVariant getVariant() {
        return variant;
    }

    public void setVariant(SubscriptionVariant variant) {
        this.variant = variant;
    }

    public UUID getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
