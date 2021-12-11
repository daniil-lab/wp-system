package com.wp.system.exception.auth;

import com.wp.system.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum AuthErrorCode implements ErrorCode {
    INVALID_DATA("Invalid data", "Check given data", "AUTH_1", HttpStatus.BAD_REQUEST),
    NO_AUTH("Unauthorized", "Try auth and repeat request", "AUTH_2", HttpStatus.UNAUTHORIZED),
    PASSWORD_DECODE_ERROR("Password Decode Failed", "Can`t convert password from base64", "AUTH_3", HttpStatus.UNAUTHORIZED),
    NO_ACCESS("No access", "Give needed permissions to user role", "AUTH_4", HttpStatus.FORBIDDEN);

    private String errorName;

    private String errorDescription;

    private String errorCode;

    private HttpStatus errorHttpStatus;

    AuthErrorCode(String errorName, String errorDescription, String errorCode, HttpStatus errorHttpStatus) {
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
