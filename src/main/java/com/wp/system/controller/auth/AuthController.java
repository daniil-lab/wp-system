package com.wp.system.controller.auth;

import com.wp.system.request.auth.AuthRequest;
import com.wp.system.response.ServiceResponse;
import com.wp.system.response.auth.AuthDataResponse;
import com.wp.system.services.auth.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Tag(name = "Auth API")
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/")
    public ResponseEntity<ServiceResponse<AuthDataResponse>> authUser(@Valid @RequestBody AuthRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), authService.authUser(request), "Success auth"), HttpStatus.OK);
    }
}
