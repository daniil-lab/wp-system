package com.wp.system.request.tinkoff;

import java.util.UUID;

public class TinkoffSubmitAuthRequest {
    private UUID id;

    private String code;

    private String password;

    public TinkoffSubmitAuthRequest() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
