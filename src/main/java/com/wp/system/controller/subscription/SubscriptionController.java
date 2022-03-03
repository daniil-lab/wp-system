package com.wp.system.controller.subscription;

import com.wp.system.controller.DocumentedRestController;
import com.wp.system.dto.subscription.SubscriptionDTO;
import com.wp.system.request.subscription.ExtendSubscriptionRequest;
import com.wp.system.request.subscription.UseVariantOnUserRequest;
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

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@Tag(name = "Subscription API")
@RequestMapping("/api/v1/subscription")
@SecurityRequirement(name = "Bearer")
public class SubscriptionController extends DocumentedRestController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PreAuthorize("hasAnyAuthority('SUBSCRIPTION_GET', 'SUBSCRIPTION_FULL')")
    @Operation(summary = "Получение данных подписки по ID")
    @SecurityRequirement(name = "Bearer")
    @GetMapping(value = "/{subId}")
    public ResponseEntity<ServiceResponse<SubscriptionDTO>> getSubscriptionById(
            @PathVariable
                    UUID subId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new SubscriptionDTO(subscriptionService.getSubscriptionById(subId)), "Subscription returned"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('SUBSCRIPTION_GET', 'SUBSCRIPTION_FULL')")
    @Operation(summary = "Получение данных подписки по ID пользователя")
    @SecurityRequirement(name = "Bearer")
    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<ServiceResponse<SubscriptionDTO>> getSubscriptionByUserId(
            @PathVariable
                    UUID userId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new SubscriptionDTO(subscriptionService.getSubscriptionByUserId(userId)), "Subscription returned"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('SUBSCRIPTION_UPDATE', 'SUBSCRIPTION_FULL')")
    @Operation(summary = "Сброс подписки пользователя")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping(value = "/reset/{subscriptionId}")
    public ResponseEntity<ServiceResponse<SubscriptionDTO>> resetSubscription(
            @PathVariable
                    UUID subscriptionId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new SubscriptionDTO(subscriptionService.resetSubscription(subscriptionId)), "Subscription returned"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('SUBSCRIPTION_UPDATE', 'SUBSCRIPTION_FULL')")
    @Operation(summary = "Применение варианта подписки на пользователя")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping(value = "/use")
    public ResponseEntity<ServiceResponse<SubscriptionDTO>> useVariantOnUser(
            @RequestBody
            @Valid
                    UseVariantOnUserRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new SubscriptionDTO(subscriptionService.useVariantOnUser(request)), "Subscription returned"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('SUBSCRIPTION_UPDATE', 'SUBSCRIPTION_FULL')")
    @Operation(summary = "Продление подписки пользователя")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping(value = "/extend/{subscriptionId}")
    public ResponseEntity<ServiceResponse<SubscriptionDTO>> extendSubscription(
            @RequestBody
            @Valid
                    ExtendSubscriptionRequest request,
            @PathVariable
                    UUID subscriptionId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new SubscriptionDTO(subscriptionService.extendSubscription(request, subscriptionId)), "Subscription returned"), HttpStatus.OK);
    }


    @PreAuthorize("hasAnyAuthority('SUBSCRIPTION_GET', 'SUBSCRIPTION_FULL')")
    @Operation(summary = "Получение всех подписок")
    @SecurityRequirement(name = "Bearer")
    @GetMapping(value = "/")
    public ResponseEntity<ServiceResponse<List<SubscriptionDTO>>> getAllSubscriptions() {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), subscriptionService.getAllSubscription().stream().map(SubscriptionDTO::new).collect(Collectors.toList()), "Subscriptions returned"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('SUBSCRIPTION_GET', 'SUBSCRIPTION_FULL')")
    @Operation(summary = "Получение всех подписок постранично")
    @SecurityRequirement(name = "Bearer")
    @GetMapping(value = "/page")
    public ResponseEntity<ServiceResponse<List<SubscriptionDTO>>> getAllSubscriptionsByPages(
            @RequestParam
                int pageSize,
            @RequestParam
                int page
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), subscriptionService.getSubscriptionsByPage(pageSize, page).stream().map(SubscriptionDTO::new).collect(Collectors.toList()), "Subscriptions returned"), HttpStatus.OK);
    }
}
