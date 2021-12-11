package com.wp.system.response.auth;

import com.wp.system.entity.user.User;

public class AuthDataResponse {
    private String token;

    private User user;

    public AuthDataResponse () {};

    public AuthDataResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
