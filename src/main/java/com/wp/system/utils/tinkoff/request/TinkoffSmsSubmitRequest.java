package com.wp.system.utils.tinkoff.request;

public class TinkoffSmsSubmitRequest {
    private String initialOperation;

    private String initialOperationTicket;

    private String confirmationData;

    public TinkoffSmsSubmitRequest() {}

    public String getInitialOperation() {
        return initialOperation;
    }

    public void setInitialOperation(String initialOperation) {
        this.initialOperation = initialOperation;
    }

    public String getInitialOperationTicket() {
        return initialOperationTicket;
    }

    public void setInitialOperationTicket(String initialOperationTicket) {
        this.initialOperationTicket = initialOperationTicket;
    }

    public String getConfirmationData() {
        return confirmationData;
    }

    public void setConfirmationData(String confirmationData) {
        this.confirmationData = confirmationData;
    }
}
