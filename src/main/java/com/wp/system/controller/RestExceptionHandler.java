package com.wp.system.controller;

import com.sun.mail.smtp.SMTPAddressFailedException;
import com.wp.system.entity.logging.ErrorLogSource;
import com.wp.system.exception.ServiceErrorResponse;
import com.wp.system.exception.ServiceException;
import com.wp.system.request.logging.CreateErrorLogRequest;
import com.wp.system.services.logging.SystemErrorLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mail.MailSendException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.mail.SendFailedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@RestControllerAdvice
public class RestExceptionHandler {

    @Autowired
    private SystemErrorLogger systemErrorLogger;

//    @ExceptionHandler(MailSendException.class)
//    public ResponseEntity<ServiceErrorResponse> handleSMPTAddressFailedException(MailSendException e) {
//        ServiceException exception = new ServiceException("SMPT error", HttpStatus.INTERNAL_SERVER_ERROR);
//        systemErrorLogger.createErrorLog(exception);
//        return new ResponseEntity<>(new ServiceErrorResponse(exception), exception.getHttpCode());
//    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ServiceErrorResponse> handleServiceException(ServiceException e) {
        systemErrorLogger.createErrorLog(e);
        return new ResponseEntity<>(new ServiceErrorResponse(e), e.getHttpCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ServiceErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> advices = new ArrayList<>();

        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

//            advices.add("%s - %s".formatted(fieldName, errorMessage));
        });

        ServiceException exception = new ServiceException("Request body validation failed", HttpStatus.BAD_REQUEST);

        ServiceErrorResponse errorResponse = new ServiceErrorResponse(exception);

        advices.forEach(errorResponse::addAdvice);

        systemErrorLogger.createErrorLog(exception);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ServiceErrorResponse> handleNotReadableException(HttpMessageNotReadableException e) {
        ServiceException exception = new ServiceException("Can`t read Request body", HttpStatus.BAD_REQUEST);

        systemErrorLogger.createErrorLog(exception);


        return new ResponseEntity<>(new ServiceErrorResponse(exception), HttpStatus.BAD_REQUEST);
    }


}