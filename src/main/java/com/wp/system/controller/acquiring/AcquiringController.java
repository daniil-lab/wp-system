package com.wp.system.controller.acquiring;

import com.wp.system.response.ServiceResponse;
import com.wp.system.services.acquiring.AcquiringService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.naming.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Tag(name = "Acquiring API")
@RequestMapping("/api/v1/acquiring")
@SecurityRequirement(name = "Bearer")
public class AcquiringController {
    @Autowired
    private AcquiringService acquiringService;

    @GetMapping("/payment-url")
    public ResponseEntity<ServiceResponse<String>> getPaymentUrl(
            @RequestParam
                    UUID subscriptionVariantId,
            @RequestParam
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), acquiringService.generatePaymentUrl(
                userId,
                subscriptionVariantId
        )), HttpStatus.OK);
    }
}
