package com.wp.system.dto.activity;

import com.wp.system.dto.subscription.SubscriptionDTO;
import com.wp.system.dto.user.UserDTO;
import com.wp.system.entity.activity.Activity;
import com.wp.system.entity.subscription.Subscription;

import java.time.Instant;
import java.util.UUID;

public class ActivityDTO {
    private UUID id;

    private String screenName;

    private Instant startTime;

    private Instant endTime;

    private UserDTO user;

    private SubscriptionDTO subscription;

    public ActivityDTO() {}

    public ActivityDTO(Activity a) {
        if(a == null)
            return;

        this.id = a.getId();
        this.screenName = a.getScreenName();
        this.startTime = a.getStartTime();
        this.endTime = a.getEndTime();
        this.user = new UserDTO(a.getUser());
        this.subscription = new SubscriptionDTO(a.getSubscription());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public SubscriptionDTO getSubscription() {
        return subscription;
    }

    public void setSubscription(SubscriptionDTO subscription) {
        this.subscription = subscription;
    }
}
