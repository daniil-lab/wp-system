package com.wp.system.exception.logging;

import com.wp.system.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum SystemErrorLoggingErrorCode implements ErrorCode {
    NOT_FOUND("Error log not found", "Check given data", "ERROR_LOGGING_1", HttpStatus.NOT_FOUND);

    private String errorName;

    private String errorDescription;

    private String errorCode;

    private HttpStatus errorHttpStatus;

    SystemErrorLoggingErrorCode(String errorName, String errorDescription, String errorCode, HttpStatus errorHttpStatus) {
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
