package com.wp.system.request.notification;

import com.wp.system.utils.ValidationErrorMessages;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public class SendNotificationToSomeUsersRequest {
    @Schema(required = true, description = "ID пользователей")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private List<UUID> userId;

    @Schema(required = true, description = "Тело уведомления")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private String body;

    @Schema(required = true, description = "Заголовок уведомления")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private String header;

    public SendNotificationToSomeUsersRequest() {}

    public SendNotificationToSomeUsersRequest(List<UUID> userId, String body, String header) {
        this.userId = userId;
        this.body = body;
        this.header = header;
    }

    public List<UUID> getUserId() {
        return userId;
    }

    public void setUserId(List<UUID> userId) {
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
