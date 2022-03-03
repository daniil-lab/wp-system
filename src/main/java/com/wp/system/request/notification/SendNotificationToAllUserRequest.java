package com.wp.system.request.notification;

import com.wp.system.utils.ValidationErrorMessages;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class SendNotificationToAllUserRequest {
    @Schema(required = true, description = "Тело уведомления")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private String body;

    @Schema(required = true, description = "Заголовок уведомления")
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
