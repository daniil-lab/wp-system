package com.wp.system.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wp.system.exception.ServiceErrorResponse;
import com.wp.system.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class AuthExceptionHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(401);
        response.getOutputStream().println(new ObjectMapper().writeValueAsString(new ServiceErrorResponse(new ServiceException("No auth", HttpStatus.UNAUTHORIZED))));
    }
}
