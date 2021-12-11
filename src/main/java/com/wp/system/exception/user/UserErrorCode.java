package com.wp.system.exception.user;

import com.wp.system.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum UserErrorCode implements ErrorCode {
    NOT_FOUND("User not found", "Check given data", "USER_1", HttpStatus.NOT_FOUND),
    ALREADY_EXIST("User already exist", "Check given data", "USER_2", HttpStatus.BAD_REQUEST),

    ROLE_NOT_FOUND("User Role not found", "Check given data", "USER_ROLE_1", HttpStatus.NOT_FOUND),
    ROLE_ALREADY_EXIST("User Role already exist", "Check given data", "USER_ROLE_2", HttpStatus.BAD_REQUEST),
    AUTOAPPLY_ROLE_ALREADY_EXIST("User Role with autoApply already exist", "Check given data", "USER_ROLE_3", HttpStatus.BAD_REQUEST),
    AUTOAPPLY_ROLE_NOT_FOUND("User Role with autoApply not found", "Check given data", "USER_ROLE_4", HttpStatus.NOT_FOUND),

    PERMISSION_ROLE_NOT_FOUND("User Role Permission not found", "Check given data", "USER_ROLE_PERMISSION_1", HttpStatus.NOT_FOUND),
    PERMISSION_ROLE_ALREADY_EXIST("User Role Permission already exist in this role", "Check given data", "USER_ROLE_PERMISSION_2", HttpStatus.BAD_REQUEST);

    private String errorName;

    private String errorDescription;

    private String errorCode;

    private HttpStatus errorHttpStatus;

    UserErrorCode(String errorName, String errorDescription, String errorCode, HttpStatus errorHttpStatus) {
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
