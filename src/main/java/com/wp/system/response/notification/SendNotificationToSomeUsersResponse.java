package com.wp.system.response.notification;

import com.wp.system.dto.user.UserDTO;
import com.wp.system.entity.user.User;

import java.util.List;
import java.util.stream.Collectors;

public class SendNotificationToSomeUsersResponse {
    private List<UserDTO> user;

    private String header;

    private String body;

    public SendNotificationToSomeUsersResponse() {};

    public SendNotificationToSomeUsersResponse(List<User> user, String header, String body) {
        this.user = user.stream().map((u) -> {
            if(u == null)
                return null;

            return new UserDTO(u);
        }).collect(Collectors.toList());
        this.header = header;
        this.body = body;
    }

    public List<UserDTO> getUser() {
        return user;
    }

    public void setUser(List<UserDTO> user) {
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
