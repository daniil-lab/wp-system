package com.wp.system.config.security;

public class AuthCredentials {
    private String username;

    public AuthCredentials() {};

    public AuthCredentials(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
