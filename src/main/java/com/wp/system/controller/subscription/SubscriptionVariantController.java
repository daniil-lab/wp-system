package com.wp.system.controller.subscription;

import com.wp.system.controller.DocumentedRestController;
import com.wp.system.dto.subscription.SubscriptionDTO;
import com.wp.system.dto.subscription.SubscriptionVariantDTO;
import com.wp.system.dto.subscription.SubscriptionVariantGroupDTO;
import com.wp.system.request.subscription.CreateSubscriptionVariantGroupRequest;
import com.wp.system.request.subscription.CreateSubscriptionVariantRequest;
import com.wp.system.request.subscription.UpdateSubscriptionVariantRequest;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.subscription.SubscriptionService;
import com.wp.system.services.subscription.SubscriptionVariantService;
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
@Tag(name = "Subscription Variant API")
@RequestMapping("/api/v1/subscription-variant")
@SecurityRequirement(name = "Bearer")
public class SubscriptionVariantController extends DocumentedRestController {

    @Autowired
    private SubscriptionVariantService subscriptionVariantService;

    @PreAuthorize("hasAnyAuthority('SUBSCRIPTION_VARIANT_GET', 'SUBSCRIPTION_VARIANT_FULL')")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Operation(summary = "Получение варианта подписки по ID")
    @SecurityRequirement(name = "Bearer")
    @GetMapping(value = "/{subId}")
    public ResponseEntity<ServiceResponse<SubscriptionVariantDTO>> getSubscriptionVariantById(
            @PathVariable
                    UUID subId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new SubscriptionVariantDTO(subscriptionVariantService.getSubscriptionVariantById(subId)), "Subscription variant returned"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('SUBSCRIPTION_VARIANT_GET', 'SUBSCRIPTION_VARIANT_FULL')")
    @Operation(summary = "Получение варианта подписки по ID")
    @SecurityRequirement(name = "Bearer")
    @GetMapping(value = "/group/{groupId}")
    public ResponseEntity<ServiceResponse<SubscriptionVariantGroupDTO>> getSubscriptionVariantGroupById(
            @PathVariable
                    UUID groupId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new SubscriptionVariantGroupDTO(subscriptionVariantService.getGroupById(groupId)), "Subscription variant returned"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('SUBSCRIPTION_VARIANT_GET', 'SUBSCRIPTION_VARIANT_FULL')")
    @Operation(summary = "Получение группы вариантов подписки")
    @SecurityRequirement(name = "Bearer")
    @GetMapping(value = "/group")
    public ResponseEntity<ServiceResponse<List<SubscriptionVariantGroupDTO>>> getVariantGroups() {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), subscriptionVariantService.getVariantGroups().stream().map(SubscriptionVariantGroupDTO::new).collect(Collectors.toList()), "Subscription variant returned"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('SUBSCRIPTION_VARIANT_GET', 'SUBSCRIPTION_VARIANT_FULL')")
    @Operation(summary = "Получение всех вариантов подписки")
    @SecurityRequirement(name = "Bearer")
    @GetMapping(value = "/")
    public ResponseEntity<ServiceResponse<List<SubscriptionVariantDTO>>> getSubscriptionVariants() {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), subscriptionVariantService.getAllSubscriptionVariants().stream().map(SubscriptionVariantDTO::new).collect(Collectors.toList()), "Subscription variants returned"), HttpStatus.OK);
    }


    @PreAuthorize("hasAnyAuthority('SUBSCRIPTION_VARIANT_CREATE', 'SUBSCRIPTION_VARIANT_FULL')")
    @Operation(summary = "Создание варианта подписки")
    @SecurityRequirement(name = "Bearer")
    @PostMapping(value = "/")
    public ResponseEntity<ServiceResponse<SubscriptionVariantDTO>> createSubscriptionVariant(
            @RequestBody
            @Valid
                CreateSubscriptionVariantRequest request
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.CREATED.value(), new SubscriptionVariantDTO(subscriptionVariantService.createSubscriptionVariant(request)), "Subscription returned"), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('SUBSCRIPTION_VARIANT_CREATE', 'SUBSCRIPTION_VARIANT_FULL')")
    @Operation(summary = "Создание группы вариантов подписки")
    @SecurityRequirement(name = "Bearer")
    @PostMapping(value = "/group")
    public ResponseEntity<ServiceResponse<SubscriptionVariantGroupDTO>> createSubscriptionVariantGroup(
            @RequestBody
            @Valid
                    CreateSubscriptionVariantGroupRequest request
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.CREATED.value(), new SubscriptionVariantGroupDTO(subscriptionVariantService.createSubscriptionVariantGroup(request)), "Subscription returned"), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('SUBSCRIPTION_VARIANT_UPDATE', 'SUBSCRIPTION_VARIANT_FULL')")
    @Operation(summary = "Создание варианта подписки")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping(value = "/{varId}")
    public ResponseEntity<ServiceResponse<SubscriptionVariantDTO>> updateSubscriptionVariant(
            @RequestBody
            @Valid
                    UpdateSubscriptionVariantRequest request,
            @PathVariable
                UUID varId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new SubscriptionVariantDTO(subscriptionVariantService.updateSubscriptionVariant(request, varId)), "Subscription returned"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('SUBSCRIPTION_VARIANT_DELETE', 'SUBSCRIPTION_VARIANT_FULL')")
    @Operation(summary = "Удаление варианта подписки")
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping(value = "/{varId}")
    public ResponseEntity<ServiceResponse<SubscriptionVariantDTO>> removeSubscriptionVariant(
            @PathVariable
                    UUID varId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new SubscriptionVariantDTO(subscriptionVariantService.removeSubscriptionVariant(varId)), "Subscription returned"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('SUBSCRIPTION_VARIANT_DELETE', 'SUBSCRIPTION_VARIANT_FULL')")
    @Operation(summary = "Удаление группы вариантов подписки")
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping(value = "/group/{groupId}")
    public ResponseEntity<ServiceResponse<SubscriptionVariantGroupDTO>> removeSubscriptionVariantGroup(
            @PathVariable
                    UUID groupId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new SubscriptionVariantGroupDTO(subscriptionVariantService.removeVariantGroup(groupId)), "Subscription returned"), HttpStatus.OK);
    }
}
