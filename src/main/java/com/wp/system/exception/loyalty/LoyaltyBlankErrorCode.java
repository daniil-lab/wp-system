package com.wp.system.exception.loyalty;

import com.wp.system.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum LoyaltyBlankErrorCode implements ErrorCode {
    NOT_FOUND("Loyalty Blank not found", "Check given data", "LOYALTY_BLANK_1",HttpStatus.NOT_FOUND);

    private String errorName;

    private String errorDescription;

    private String errorCode;

    private HttpStatus errorHttpStatus;

    LoyaltyBlankErrorCode(String errorName, String errorDescription, String errorCode, HttpStatus errorHttpStatus) {
        this.errorName = errorName;
        this.errorDescription = errorDescription;
        this.errorCode = errorCode;
        this.errorHttpStatus = errorHttpStatus;
    }

    @Override
    public String getErrorName() {
        return this.errorName;
    }

    @Override
    public String getErrorDescription() {
        return this.errorDescription;
    }

    @Override
    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public HttpStatus getErrorHttpStatus() {
        return this.errorHttpStatus;
    }
}
