package com.wp.system.utils.convert;

import java.math.BigDecimal;

public class ConvertResponse {
    private Boolean success;

    private BigDecimal result;

    public ConvertResponse() {}

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }
}
