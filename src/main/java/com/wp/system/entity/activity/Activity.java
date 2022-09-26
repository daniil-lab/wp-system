package com.wp.system.entity.activity;

import com.wp.system.entity.subscription.Subscription;
import com.wp.system.entity.user.User;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
public class Activity {
    @Id
    private UUID id = UUID.randomUUID();

    private String screenName;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant startTime;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant endTime;

    public Activity() {}

    public Activity(String screenName, User user, Instant startTime, Instant endTime) {
        this.screenName = screenName;
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }
}
