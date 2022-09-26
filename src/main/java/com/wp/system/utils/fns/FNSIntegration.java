package com.wp.system.utils.fns;

public class FNSIntegration {
    protected String tempToken;

    public FNSIntegration(String tempToken) {
        this.tempToken = tempToken;
    }

    public String getTempToken() {
        return tempToken;
    }

    public void setTempToken(String tempToken) {
        this.tempToken = tempToken;
    }
}
