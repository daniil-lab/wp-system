package com.wp.system.utils.tinkoff.response.operations;

import com.wp.system.utils.tinkoff.response.session.TinkoffSessionStatusPayloadResponse;

public class TinkoffOperationsWrapperResponse {
    private String trackingId;

    private String resultCode;

    private TinkoffSessionStatusPayloadResponse payload;

    public TinkoffOperationsWrapperResponse() {}

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public TinkoffSessionStatusPayloadResponse getPayload() {
        return payload;
    }

    public void setPayload(TinkoffSessionStatusPayloadResponse payload) {
        this.payload = payload;
    }
}
