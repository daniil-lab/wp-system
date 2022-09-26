package com.wp.system.request.activity;

import java.time.Instant;
import java.util.UUID;

public class CreateActivityRequest {
    private String screenName;

    private Instant startTime;

    private Instant endTime;

    public CreateActivityRequest() {}

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
}
