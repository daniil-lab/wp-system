package com.wp.system.utils.tinkoff.response;

public class TinkoffAuthResponse {
    private String trackingId;

    private String operationTicket;

    private String initialOperation;

    public TinkoffAuthResponse() {}

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
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
}
