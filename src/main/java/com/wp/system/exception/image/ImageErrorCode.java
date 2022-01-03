package com.wp.system.exception.image;

import com.wp.system.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ImageErrorCode implements ErrorCode {
    NOT_FOUND("Image not found", "Check given data", "IMAGE_1",HttpStatus.NOT_FOUND),
    UPLOAD_ERROR("Image not saved", "Internal error", "IMAGE_2",HttpStatus.INTERNAL_SERVER_ERROR),
    SEND_ERROR("Can`t read image", "Internal error", "IMAGE_3",HttpStatus.INTERNAL_SERVER_ERROR);

    private String errorName;

    private String errorDescription;

    private String errorCode;

    private HttpStatus errorHttpStatus;

    ImageErrorCode(String errorName, String errorDescription, String errorCode, HttpStatus errorHttpStatus) {
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
