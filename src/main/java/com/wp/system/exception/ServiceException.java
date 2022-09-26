package com.wp.system.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceException extends RuntimeException {
    private HttpStatus httpCode;

    private String description;

    private String lastTrace;

    public ServiceException(String errorName, HttpStatus httpStatus) {
        super(errorName);
        this.httpCode = httpStatus;
        this.lastTrace = Arrays.toString(new List[]{Arrays.stream(this.getStackTrace()).collect(Collectors.toList()).subList(0, 10)});
    }

    public ServiceException(String errorName, HttpStatus httpStatus, String description) {
        super(errorName);
        this.httpCode = httpStatus;
        this.description = description;
        this.lastTrace = Arrays.toString(this.getStackTrace());
    }

    public HttpStatus getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(HttpStatus httpCode) {
        this.httpCode = httpCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastTrace() {
        return lastTrace;
    }

    public void setLastTrace(String lastTrace) {
        this.lastTrace = lastTrace;
    }
}
