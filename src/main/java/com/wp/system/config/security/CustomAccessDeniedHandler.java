package com.wp.system.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wp.system.exception.ServiceErrorResponse;
import com.wp.system.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(403);
        response.getOutputStream().println(new ObjectMapper().writeValueAsString(new ServiceErrorResponse(new ServiceException("No access", HttpStatus.FORBIDDEN))));
    }
}
