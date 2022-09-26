package com.wp.system.request.auth;

import javax.validation.constraints.NotNull;

public class RegisterCredAuthRequest {
    @NotNull
    private String email;

    @NotNull
    private String registerCred;

    @NotNull
    private CredType credType;

    public CredType getCredType() {
        return credType;
    }

    public void setCredType(CredType credType) {
        this.credType = credType;
    }

    public RegisterCredAuthRequest() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegisterCred() {
        return registerCred;
    }

    public void setRegisterCred(String registerCred) {
        this.registerCred = registerCred;
    }
}
