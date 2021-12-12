package com.wp.system.request.auth;

import java.util.UUID;

public class PhoneAuthCheckRequest {
    private int code;

    private UUID requestId;

    public PhoneAuthCheckRequest() {};

    public PhoneAuthCheckRequest(int code, UUID requestId) {
        this.code = code;
        this.requestId = requestId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }
}
