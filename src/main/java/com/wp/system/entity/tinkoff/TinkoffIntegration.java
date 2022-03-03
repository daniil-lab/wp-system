package com.wp.system.entity.tinkoff;

import com.wp.system.entity.user.User;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
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

    private String token;

    private String wuid;

    private TinkoffSyncStage stage;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "integration")
    @Fetch(FetchMode.SUBSELECT)
    private Set<TinkoffCard> cards = new HashSet<>();

    public TinkoffIntegration() {}

    public Set<TinkoffCard> getCards() {
        return cards;
    }

    public void setCards(Set<TinkoffCard> cards) {
        this.cards = cards;
    }

    public TinkoffSyncStage getStage() {
        return stage;
    }

    public void setStage(TinkoffSyncStage stage) {
        this.stage = stage;
    }

    public TinkoffIntegration(User user, String token, String wuid) {
        this.startDate = Instant.now();
        this.user = user;
        this.token = token;
        this.wuid = wuid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getWuid() {
        return wuid;
    }

    public void setWuid(String wuid) {
        this.wuid = wuid;
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
