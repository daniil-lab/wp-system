package com.wp.system.response.auth;

import java.util.UUID;

public class PhoneAuthRequestResponse {
    private UUID requestId;

    public PhoneAuthRequestResponse() {}

    public PhoneAuthRequestResponse(UUID requestId) {
        this.requestId = requestId;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }
}
