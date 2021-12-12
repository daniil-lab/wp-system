package com.wp.system.request.notification;

import com.wp.system.other.ValidationErrorMessages;

import javax.validation.constraints.NotNull;

public class SendNotificationToAllUserRequest {
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private String body;

    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private String header;

    public SendNotificationToAllUserRequest() {};

    public SendNotificationToAllUserRequest(String body, String header) {
        this.body = body;
        this.header = header;
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
