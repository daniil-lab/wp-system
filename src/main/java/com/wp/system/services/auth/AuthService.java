package com.wp.system.services.auth;

import com.wp.system.config.jwt.JwtProvider;
import com.wp.system.entity.user.User;
import com.wp.system.exception.ServiceException;
import com.wp.system.exception.auth.AuthErrorCode;
import com.wp.system.request.auth.AuthRequest;
import com.wp.system.response.auth.AuthDataResponse;
import com.wp.system.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    public AuthDataResponse authUser(AuthRequest request) {
        User user = this.userService.getUserByUsername(request.getUsername());

        byte[] passwordBytes = Base64.getDecoder().decode(request.getPassword());

        if(passwordEncoder.matches(new String(passwordBytes), user.getPassword()))
        return new AuthDataResponse(jwtProvider.generateToken(user.getUsername()), user);

        throw new ServiceException(AuthErrorCode.INVALID_DATA);
    }
}
