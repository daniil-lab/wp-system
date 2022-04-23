package com.wp.system.utils.tinkoff.request;

public class TinkoffSmsDataRequest {
    private Integer SMSBYID;

    public TinkoffSmsDataRequest() {}

    public TinkoffSmsDataRequest(Integer SMSBYID) {
        this.SMSBYID = SMSBYID;
    }

    public Integer getSMSBYID() {
        return SMSBYID;
    }

    public void setSMSBYID(Integer SMSBYID) {
        this.SMSBYID = SMSBYID;
    }
}
