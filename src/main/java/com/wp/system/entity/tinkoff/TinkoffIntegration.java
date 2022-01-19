package com.wp.system.entity.tinkoff;

import com.wp.system.entity.user.User;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
public class TinkoffIntegration {
    @Id
    private UUID id = UUID.randomUUID();

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant startDate;

    @OneToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    private User user;

    public TinkoffIntegration() {}

    public TinkoffIntegration(Instant startDate, User user) {
        this.startDate = startDate;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
