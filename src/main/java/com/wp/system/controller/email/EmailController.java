package com.wp.system.controller.email;

import com.wp.system.controller.DocumentedRestController;
import com.wp.system.dto.category.CategoryDTO;
import com.wp.system.request.category.EditCategoryRequest;
import com.wp.system.request.email.SubmitEmailVerificationRequest;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.email.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@Tag(name = "Email API")
@RequestMapping("/api/v1/email")
@SecurityRequirement(name = "Bearer")
public class EmailController extends DocumentedRestController {
    @Autowired
    private EmailService emailService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('EMAIL_VERIFICATION_RESEND', 'EMAIL_FULL')")
    @Operation(summary = "Переотправка письма подтверждения")
    @GetMapping("/verification/resend/{userId}")
    public ResponseEntity<ServiceResponse<Boolean>> resendEmailVerification(@PathVariable UUID userId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), emailService.resendEmailVerification(userId), "Mail sent"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('EMAIL_VERIFICATION_SEND', 'EMAIL_FULL')")
    @Operation(summary = "Отправка письма подтверждения")
    @GetMapping("/verification/{userId}")
    public ResponseEntity<ServiceResponse<Boolean>> sendEmailVerification(@PathVariable UUID userId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), emailService.sendEmailVerificationMail(userId), "Mail sent"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('EMAIL_VERIFICATION_SUBMIT', 'EMAIL_FULL')")
    @Operation(summary = "Подтверждение электронного адреса")
    @PostMapping("/verification")
    public ResponseEntity<ServiceResponse<Boolean>> submitEmailVerification(@RequestBody @Valid SubmitEmailVerificationRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), emailService.submitEmailVerification(request), "Verification submitted"), HttpStatus.OK);
    }
}
