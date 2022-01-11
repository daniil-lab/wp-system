package com.wp.system.entity.logging;

import com.wp.system.exception.ErrorCode;
import com.wp.system.exception.ServiceException;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.UUID;

@Entity
public class SystemErrorLog {
    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    private String errorCode;

    private String trace;

    public SystemErrorLog() {}

    public SystemErrorLog(String name, String errorCode, String trace) {
        this.name = name;
        this.errorCode = errorCode;
        this.trace = trace;
    }

    public SystemErrorLog(ServiceException error) {
        this.name = error.getMessage();
        this.errorCode = error.getCode();
        this.trace = Arrays.toString(error.getStackTrace());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }
}
