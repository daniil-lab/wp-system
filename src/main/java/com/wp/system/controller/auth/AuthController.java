package com.wp.system.controller.auth;

import com.wp.system.controller.DocumentedRestController;
import com.wp.system.exception.ServiceErrorResponse;
import com.wp.system.request.auth.*;
import com.wp.system.response.ServiceResponse;
import com.wp.system.response.auth.AuthDataResponse;
import com.wp.system.response.auth.CheckOnRegisterRequest;
import com.wp.system.response.auth.PhoneAuthRequestResponse;
import com.wp.system.response.auth.SmsSubmitResponse;
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

    @Operation(summary = "Начальный этап SMS верификации")
    @PostMapping("/sms-submit")
    public ResponseEntity<ServiceResponse<SmsSubmitResponse>> smsSubmitTry(
            @Valid
            @Parameter(required = true, description = """
                        Обязательные поля: phone.\n
                        В ответ на запрос получаем UUID.\n
                        Как получили код от пользователя, делаем запрос /sms-submit/result и передаем туда полученный из 
                        этого запроса ID вместе с кодом.
                    """)
            @RequestBody
                SmsSubmitRequest request
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), authService.smsSubmitAttempt(request), "Try success"), HttpStatus.OK);
    }

    @Operation(summary = "Заключительный этап SMS верификации")
    @PostMapping("/sms-submit/result")
    public ResponseEntity<ServiceResponse<Boolean>> smsSubmitResult(
            @Valid
            @Parameter(required = true, description = """
                        Обязательные поля: id, code.\n
                        В id передаем UUID, полученный из /sms-submit\n
                        В code передаем введеный пользователем код из СМС\n
                        При совпадении данных получаем data: true и статус 200
                    """)
            @RequestBody
                    SmsSubmitResultRequest request
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), authService.smsSubmitResult(request), "Verification success"), HttpStatus.OK);
    }

    @Operation(summary = "Авторизация с помощью SMS верификации")
    @PostMapping("/sms")
    public ResponseEntity<ServiceResponse<AuthDataResponse>> authBySmsSubmit(
            @Valid
            @Parameter(required = true, description = """
                        Обязательные поля: id, code.\n
                        В id передаем UUID, полученный из /sms-submit\n
                        В code передаем введеный пользователем код из СМС\n
                        При совпадении данных получаем данные по авторизации
                    """)
            @RequestBody
                    SmsSubmitResultRequest request
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), authService.authBySmsSubmit(request), "Auth success"), HttpStatus.OK);
    }

    @Operation(summary = "Проверка на регистрацию")
    @PostMapping("/check-register")
    public ResponseEntity<ServiceResponse<Boolean>> checkRegister(
            @Valid
            @Parameter(required = true, description = """
                    Обязательные поля: phone.\n
                    Если пользователь сущетствует, вернет data: true и статус 200, если нет, то ошибку 404.
                    """)
            @RequestBody
                    CheckOnRegisterRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), authService.checkOnRegister(request), "Success check"), HttpStatus.OK);
    }
}
