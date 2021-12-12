package com.wp.system.response.auth;

import com.wp.system.dto.user.UserDTO;
import com.wp.system.entity.user.User;

public class AuthDataResponse {
    private String token;

    private UserDTO user;

    public AuthDataResponse () {};

    public AuthDataResponse(String token, User user) {
        this.token = token;
        this.user = new UserDTO(user);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
