package com.wp.system.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class SmsSubmitResultRequest {
    @Schema(required = true, description = "ID, полученный из первого этапа верификации по SMS")
    @NotNull
    private UUID id;

    @Schema(required = true, description = "Полученный код")
    @NotNull
    private int code;

    public SmsSubmitResultRequest() {};

    public SmsSubmitResultRequest(UUID id, int code) {
        this.id = id;
        this.code = code;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
