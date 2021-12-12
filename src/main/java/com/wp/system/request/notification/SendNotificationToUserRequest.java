package com.wp.system.request.notification;

import com.wp.system.other.ValidationErrorMessages;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class SendNotificationToUserRequest {
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private UUID userId;

    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private String body;

    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private String header;

    public SendNotificationToUserRequest() {}

    public SendNotificationToUserRequest(UUID userId, String body, String header) {
        this.userId = userId;
        this.body = body;
        this.header = header;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
