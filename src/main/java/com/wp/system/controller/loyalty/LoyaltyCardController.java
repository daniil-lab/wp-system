package com.wp.system.controller.loyalty;

import com.wp.system.dto.loyalty.LoyaltyBlankDTO;
import com.wp.system.dto.loyalty.LoyaltyCardDTO;
import com.wp.system.request.loyalty.CreateLoyaltyBlankRequest;
import com.wp.system.request.loyalty.CreateLoyaltyCardRequest;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.loyalty.LoyaltyCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Loyalty Card API")
@RequestMapping("/api/v1/loyalty-card")
public class LoyaltyCardController {
    @Autowired
    private LoyaltyCardService loyaltyCardService;

    @PreAuthorize("hasAnyAuthority('LOYALTY_CARD_CREATE', 'LOYALTY_CARD_FULL')")
    @Operation(summary = "Создание карты лояльности")
    @SecurityRequirement(name = "Bearer")
    @PostMapping(value = "/")
    public ResponseEntity<ServiceResponse<LoyaltyCardDTO>> createLoyaltyCard(
            @Valid
            @RequestBody
                    CreateLoyaltyCardRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.CREATED.value(), new LoyaltyCardDTO(loyaltyCardService.createLoyaltyCard(request)), "Loyalty Card created"), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('LOYALTY_CARD_GET', 'LOYALTY_CARD_FULL')")
    @Operation(summary = "Получение карты лояльности по ID")
    @SecurityRequirement(name = "Bearer")
    @GetMapping(value = "/{cardId}")
    public ResponseEntity<ServiceResponse<LoyaltyCardDTO>> getLoyaltyBlankById(
            @PathVariable UUID cardId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new LoyaltyCardDTO(loyaltyCardService.getLoyaltyCardById(cardId)), "Loyalty Card returned"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('LOYALTY_CARD_GET', 'LOYALTY_CARD_FULL')")
    @Operation(summary = "Получение всех карт лояльности одного пользователя")
    @SecurityRequirement(name = "Bearer")
    @GetMapping(value = "/user")
    public ResponseEntity<ServiceResponse<List<LoyaltyCardDTO>>> getAllLoyaltyCards(
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), loyaltyCardService.getAllUserLoyaltyCards().stream().map(LoyaltyCardDTO::new).collect(Collectors.toList()), "Loyalty Cards returned"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('LOYALTY_CARD_DELETE', 'LOYALTY_CARD_FULL')")
    @Operation(summary = "Удаление карты лояльности")
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping(value = "/{cardId}")
    public ResponseEntity<ServiceResponse<LoyaltyCardDTO>> removeLoyaltyCard(
            @PathVariable UUID cardId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new LoyaltyCardDTO(loyaltyCardService.removeLoyaltyCard(cardId)), "Loyalty Card returned"), HttpStatus.OK);
    }
}
