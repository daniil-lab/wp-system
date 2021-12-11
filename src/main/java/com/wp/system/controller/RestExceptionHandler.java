package com.wp.system.controller;

import com.wp.system.exception.ServiceErrorResponse;
import com.wp.system.exception.ServiceException;
import com.wp.system.exception.auth.AuthErrorCode;
import com.wp.system.exception.system.SystemErrorCode;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ServiceErrorResponse> handleServiceException(ServiceException e) {
        return new ResponseEntity<>(new ServiceErrorResponse(e), e.getHttpCode());
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AccessDeniedException accessDeniedException) throws IOException {
        throw new ServiceException(AuthErrorCode.NO_AUTH);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ServiceErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> advices = new ArrayList<>();

        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            advices.add("%s - %s".formatted(fieldName, errorMessage));
        });

        ServiceErrorResponse errorResponse = new ServiceErrorResponse(new ServiceException(SystemErrorCode.VALIDATION_FAILED));

        advices.forEach(errorResponse::addAdvice);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}