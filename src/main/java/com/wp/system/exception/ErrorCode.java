package com.wp.system.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String getErrorName();

    String getErrorDescription();

    String getErrorCode();

    HttpStatus getErrorHttpStatus();
}
