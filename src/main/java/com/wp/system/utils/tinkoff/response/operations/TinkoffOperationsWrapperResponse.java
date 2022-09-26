package com.wp.system.utils.tinkoff.response.operations;

import com.wp.system.utils.tinkoff.response.session.TinkoffSessionStatusPayloadResponse;

import java.util.List;

public class TinkoffOperationsWrapperResponse {
    private String trackingId;

    private String resultCode;

    private List<TinkoffOperationResponse> payload;

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

    public List<TinkoffOperationResponse> getPayload() {
        return payload;
    }

    public void setPayload(List<TinkoffOperationResponse> payload) {
        this.payload = payload;
    }
}
