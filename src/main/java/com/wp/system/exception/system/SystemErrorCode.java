package com.wp.system.exception.system;

import com.wp.system.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum SystemErrorCode implements ErrorCode {
    VALIDATION_FAILED("Validation data failed", "Check given data", "SYSTEM_1", HttpStatus.BAD_REQUEST),
    CANT_READ("Cant read data from request body", "Check given data", "SYSTEM_2", HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR("Internal Error", "Internal Error", "SYSTEM_3", HttpStatus.INTERNAL_SERVER_ERROR);

    private String errorName;

    private String errorDescription;

    private String errorCode;

    private HttpStatus errorHttpStatus;

    SystemErrorCode(String errorName, String errorDescription, String errorCode, HttpStatus errorHttpStatus) {
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
