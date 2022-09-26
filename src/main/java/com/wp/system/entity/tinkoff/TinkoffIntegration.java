package com.wp.system.entity.tinkoff;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wp.system.entity.user.User;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant lastOperationsSyncDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    private User user;

    @JsonIgnore
    private String token;

    private TinkoffSyncStage stage = TinkoffSyncStage.NONE;

    @JsonIgnore
    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "integration")
    @Fetch(FetchMode.SUBSELECT)
    private Set<TinkoffCard> cards = new HashSet<>();

    public Instant getLastOperationsSyncDate() {
        return lastOperationsSyncDate;
    }

    public void setLastOperationsSyncDate(Instant lastOperationsSyncDate) {
        this.lastOperationsSyncDate = lastOperationsSyncDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public TinkoffIntegration(User user, String token) {
        this.startDate = Instant.now();
        this.user = user;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
