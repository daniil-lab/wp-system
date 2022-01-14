package com.wp.system.entity.subscription;

import com.wp.system.entity.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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

    public Subscription() {}

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
