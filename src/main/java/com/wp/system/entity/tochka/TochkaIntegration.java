package com.wp.system.entity.tochka;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wp.system.entity.user.User;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class TochkaIntegration {
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
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "integration")
    @Fetch(FetchMode.SUBSELECT)
    private Set<TochkaCard> cards = new HashSet<>();

    private String token;

    private String refreshToken;

    private TochkaIntegrationState state;

    public TochkaIntegration() {}

    public Set<TochkaCard> getCards() {
        return cards;
    }

    public void setCards(Set<TochkaCard> cards) {
        this.cards = cards;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public TochkaIntegrationState getState() {
        return state;
    }

    public void setState(TochkaIntegrationState state) {
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

    public Instant getLastOperationsSyncDate() {
        return lastOperationsSyncDate;
    }

    public void setLastOperationsSyncDate(Instant lastOperationsSyncDate) {
        this.lastOperationsSyncDate = lastOperationsSyncDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
