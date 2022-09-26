package com.wp.system.utils.tinkoff.response.cards;

public class TinkoffCardExpirationResponse {
    private Long milliseconds;

    public TinkoffCardExpirationResponse() {}

    public Long getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(Long milliseconds) {
        this.milliseconds = milliseconds;
    }
}
