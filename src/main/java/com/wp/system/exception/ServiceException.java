package com.wp.system.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {
    private String code;

    private HttpStatus httpCode;

    public ServiceException(ErrorCode data) {
        super(data.getErrorName());
        this.code = data.getErrorCode();
        this.httpCode = data.getErrorHttpStatus();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public HttpStatus getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(HttpStatus httpCode) {
        this.httpCode = httpCode;
    }
}
