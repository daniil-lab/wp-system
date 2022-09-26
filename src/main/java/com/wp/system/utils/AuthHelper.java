package com.wp.system.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wp.system.config.security.AuthCredentials;
import com.wp.system.entity.user.User;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthHelper {
    @Autowired
    private UserRepository userRepository;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public AuthCredentials getAuthCredentials() {
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof AuthCredentials) {
            return (AuthCredentials) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }

        return null;
    }

    public User getUserFromAuthCredentials() {
        if(SecurityContextHolder.getContext().getAuthentication().getCredentials() instanceof AuthCredentials) {
            System.out.println(((AuthCredentials) SecurityContextHolder.getContext().getAuthentication().getCredentials()).getId());
            return userRepository.findById(((AuthCredentials) SecurityContextHolder.getContext().getAuthentication().getCredentials()).getId()).orElseThrow(() -> {
                throw new ServiceException("User not found", HttpStatus.NOT_FOUND);
            });
        }

        System.out.println("tut");

        throw new ServiceException("User not found", HttpStatus.NOT_FOUND);
    }
}
