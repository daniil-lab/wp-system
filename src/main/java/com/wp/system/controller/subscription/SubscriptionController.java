package com.wp.system.controller.subscription;

import com.wp.system.controller.DocumentedRestController;
import com.wp.system.dto.subscription.SubscriptionDTO;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.subscription.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Tag(name = "Subscription API")
@RequestMapping("/api/v1/subscription")
@SecurityRequirement(name = "Bearer")
public class SubscriptionController extends DocumentedRestController {

    @Autowired
    private SubscriptionService subscriptionService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('SUBSCRIPTION_GET', 'SUBSCRIPTION_FULL')")
    @Operation(summary = "Получение данных подписки по ID пользователя")
    @SecurityRequirement(name = "Bearer")
    @GetMapping(value = "/")
    public ResponseEntity<ServiceResponse<SubscriptionDTO>> getSubscriptionByUserId() {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new SubscriptionDTO(subscriptionService.getSubscriptionByUserId()), "Subscription returned"), HttpStatus.OK);
    }
}
