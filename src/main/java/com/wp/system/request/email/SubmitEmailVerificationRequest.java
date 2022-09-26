package com.wp.system.request.email;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class SubmitEmailVerificationRequest {
    @NotNull
    private Integer code;

    @NotNull
    private UUID userId;

    public SubmitEmailVerificationRequest() {}

    public SubmitEmailVerificationRequest(Integer code, UUID userId) {
        this.code = code;
        this.userId = userId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
