package com.wp.system.response.auth;

import java.util.UUID;

public class SmsSubmitResponse {
    private UUID id;

    public SmsSubmitResponse() {}

    public SmsSubmitResponse(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
