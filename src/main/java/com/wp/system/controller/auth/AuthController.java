package com.wp.system.controller.auth;

import com.wp.system.controller.DocumentedRestController;
import com.wp.system.request.auth.*;
import com.wp.system.response.ServiceResponse;
import com.wp.system.response.auth.AuthDataResponse;
import com.wp.system.request.auth.CheckOnRegisterByPhoneRequest;
import com.wp.system.response.auth.SmsSubmitResponse;
import com.wp.system.services.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Tag(name = "Auth API")
@RequestMapping("/api/v1/auth")
public class AuthController extends DocumentedRestController {

    @Autowired
    private AuthService authService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/")
    public ResponseEntity<ServiceResponse<AuthDataResponse>> authUser(
            @Valid
            @Parameter(required = true)
            @RequestBody
                    AuthRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), authService.authUser(request), "Success auth"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Operation(summary = "Авторизация кастомного пользователя")
    @PostMapping("/cred")
    public ResponseEntity<ServiceResponse<AuthDataResponse>> authByCred(
            @Valid
            @RequestBody
                    RegisterCredAuthRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), authService.authByCred(request), "Success auth"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Operation(summary = "Авторизация пользователя по E-MAIL")
    @PostMapping("/email")
    public ResponseEntity<ServiceResponse<AuthDataResponse>> authUserByEmail(
            @Valid
            @Parameter(required = true)
            @RequestBody
                    EmailAuthRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), authService.authUserByEmail(request), "Success auth"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Operation(summary = "Начальный этап SMS верификации")
    @PostMapping("/sms-submit")
    public ResponseEntity<ServiceResponse<SmsSubmitResponse>> smsSubmitTry(
            @Valid
            @Parameter(required = true)
            @RequestBody
                SmsSubmitRequest request
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), authService.smsSubmitAttempt(request), "Try success"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Operation(summary = "Заключительный этап SMS верификации")
    @PostMapping("/sms-submit/result")
    public ResponseEntity<ServiceResponse<Boolean>> smsSubmitResult(
            @Valid
            @Parameter(required = true)
            @RequestBody
                    SmsSubmitResultRequest request
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), authService.smsSubmitResult(request), "Verification success"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Operation(summary = "Авторизация с помощью SMS верификации")
    @PostMapping("/sms")
    public ResponseEntity<ServiceResponse<AuthDataResponse>> authBySmsSubmit(
            @Valid
            @Parameter(required = true)
            @RequestBody
                    SmsSubmitResultRequest request
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), authService.authBySmsSubmit(request), "Auth success"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Operation(summary = "Проверка на регистрацию")
    @PostMapping("/check-register")
    public ResponseEntity<ServiceResponse<Boolean>> checkRegister(
            @Valid
            @Parameter(required = true)
            @RequestBody
                    CheckOnRegisterByPhoneRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), authService.checkOnRegister(request), "Success check"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Operation(summary = "Проверка на регистрацию")
    @PostMapping("/check-register/email")
    public ResponseEntity<ServiceResponse<Boolean>> checkRegisterByEmail(
            @Valid
            @Parameter(required = true)
            @RequestBody
                    CheckOnRegisterByEmailRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), authService.checkOnRegisterEmail(request), "Success check"), HttpStatus.OK);
    }
}
