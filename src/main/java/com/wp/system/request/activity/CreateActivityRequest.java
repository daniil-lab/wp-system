package com.wp.system.request.activity;

import java.time.Instant;
import java.util.UUID;

public class CreateActivityRequest {
    private String screenName;

    private UUID userId;

    private Instant startTime;

    private Instant endTime;

    public CreateActivityRequest() {}

    public CreateActivityRequest(String screenName, UUID userId, Instant startTime, Instant endTime) {
        this.screenName = screenName;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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
