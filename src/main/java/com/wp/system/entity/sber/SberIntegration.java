package com.wp.system.entity.sber;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wp.system.entity.user.User;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
public class SberIntegration {
    @Id
    private UUID id = UUID.randomUUID();

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant startDate;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant lastOperationsSyncDate;

    private String host;

    private String session;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    private User user;

    private String token;

    private String externalToken;

    private String mGUID;

    public SberIntegration() {}

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public UUID getId() {
        return id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
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

    public String getExternalToken() {
        return externalToken;
    }

    public void setExternalToken(String externalToken) {
        this.externalToken = externalToken;
    }

    public String getmGUID() {
        return mGUID;
    }

    public void setmGUID(String mGUID) {
        this.mGUID = mGUID;
    }
}
