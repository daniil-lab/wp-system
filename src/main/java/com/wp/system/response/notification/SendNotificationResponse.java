package com.wp.system.response.notification;

import com.wp.system.dto.user.UserDTO;
import com.wp.system.entity.user.User;

public class SendNotificationResponse {

    private UserDTO user;

    private String header;

    private String body;

    public SendNotificationResponse() {};

    public SendNotificationResponse(User user, String header, String body) {
        this.user = user != null ? new UserDTO(user) : null;
        this.header = header;
        this.body = body;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
