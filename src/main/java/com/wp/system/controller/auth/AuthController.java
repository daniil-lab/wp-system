package com.wp.system.controller.auth;

import com.wp.system.controller.DocumentedRestController;
import com.wp.system.exception.ServiceErrorResponse;
import com.wp.system.request.auth.AuthRequest;
import com.wp.system.request.auth.EmailAuthRequest;
import com.wp.system.request.auth.PhoneAuthAttemptRequest;
import com.wp.system.request.auth.PhoneAuthCheckRequest;
import com.wp.system.response.ServiceResponse;
import com.wp.system.response.auth.AuthDataResponse;
import com.wp.system.response.auth.PhoneAuthRequestResponse;
import com.wp.system.services.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public class AuthController extends DocumentedRestController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/")
    public ResponseEntity<ServiceResponse<AuthDataResponse>> authUser(
            @Valid
            @Parameter(required = true, description = """
                    Обязательные поля: username, password.\n
                    В password пароль передается закодированный в Base64.\n
                    Если у пользователя установлен пин-код, то его требуется передать в code в обычном виде.
                    """)
            @RequestBody
                    AuthRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), authService.authUser(request), "Success auth"), HttpStatus.OK);
    }

    @Operation(summary = "Авторизация пользователя по E-MAIL")
    @PostMapping("/email")
    public ResponseEntity<ServiceResponse<AuthDataResponse>> authUserByEmail(
            @Valid
            @Parameter(required = true, description = """
                    Обязательные поля: email, password.\n
                    В password пароль передается закодированный в Base64.
                    """)
            @RequestBody
                    EmailAuthRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), authService.authUserByEmail(request), "Success auth"), HttpStatus.OK);
    }

    @Operation(summary = "Начальный этап авторизации по СМС")
    @PostMapping("/phone")
    public ResponseEntity<ServiceResponse<PhoneAuthRequestResponse>> phoneAuthRequest(
            @Valid
            @Parameter(required = true, description = """
                    Обязательные поля: phone.\n
                    Если у пользователя установлен пин-код, передать его в pincode.\n
                    В ответ получаем requestId, который запоминаем.\n
                    Как пользователь ввел полученный код из SMS, переходим на /phone/submit/ и отправляем туда код с requestId.
                    """)
            @RequestBody
                    PhoneAuthAttemptRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), authService.createPhoneAuthRequest(request), "Success attempt"), HttpStatus.OK);
    }

    @Operation(summary = "Заключительный этап авторизации по СМС")
    @PostMapping("/phone/submit")
    public ResponseEntity<ServiceResponse<AuthDataResponse>> phoneAuthSubmit(
            @Valid
            @Parameter(required = true, description = """
                    Обязательные поля: code, requestId.\n
                    Если код совпал с пришедшим в SMS, то отдает токен и объект пользователя.
                    """)
            @RequestBody
                    PhoneAuthCheckRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), authService.checkPhoneAuthRequest(request), "Success auth"), HttpStatus.OK);
    }
}
