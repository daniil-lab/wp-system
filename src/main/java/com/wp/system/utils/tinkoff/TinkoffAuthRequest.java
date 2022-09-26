package com.wp.system.utils.tinkoff;

import java.time.Instant;
import java.util.UUID;

public class TinkoffAuthRequest {
    private UUID id = UUID.randomUUID();

    private String phone;

    private String password;

    private UUID deviceId;

    private String sessionId;

    private Boolean reAuth;

    private String operationTicket;

    private String initialOperation;

    private Instant startDate;

    public TinkoffAuthRequest() {}

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public String getOperationTicket() {
        return operationTicket;
    }

    public void setOperationTicket(String operationTicket) {
        this.operationTicket = operationTicket;
    }

    public String getInitialOperation() {
        return initialOperation;
    }

    public void setInitialOperation(String initialOperation) {
        this.initialOperation = initialOperation;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Boolean getReAuth() {
        return reAuth;
    }

    public void setReAuth(Boolean reAuth) {
        this.reAuth = reAuth;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }
}
