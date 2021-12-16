package com.wp.system.exception.sms;

import com.wp.system.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum SmsErrorCode implements ErrorCode {
    NOT_SEND("SMS not send", "Internal Error", "SMS_1", HttpStatus.INTERNAL_SERVER_ERROR);
    private String errorName;

    private String errorDescription;

    private String errorCode;

    private HttpStatus errorHttpStatus;

    SmsErrorCode(String errorName, String errorDescription, String errorCode, HttpStatus errorHttpStatus) {
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
