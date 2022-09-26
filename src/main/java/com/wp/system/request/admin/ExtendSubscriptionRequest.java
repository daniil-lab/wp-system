package com.wp.system.request.admin;

import javax.validation.constraints.PositiveOrZero;

public class ExtendSubscriptionRequest {
    @PositiveOrZero()
    private int days;

    public ExtendSubscriptionRequest() {}

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
