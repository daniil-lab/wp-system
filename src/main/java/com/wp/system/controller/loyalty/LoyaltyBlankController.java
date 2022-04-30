package com.wp.system.controller.loyalty;

import com.wp.system.dto.image.SystemImageDTO;
import com.wp.system.dto.loyalty.LoyaltyBlankDTO;
import com.wp.system.entity.loyalty.LoyaltyBlank;
import com.wp.system.request.image.UploadImageRequest;
import com.wp.system.request.loyalty.CreateLoyaltyBlankRequest;
import com.wp.system.request.loyalty.UpdateLoyaltyBlankRequest;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.loyalty.LoyaltyBlankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Loyalty Blank API")
@RequestMapping("/api/v1/loyalty-blank")
public class LoyaltyBlankController {
    @Autowired
    private LoyaltyBlankService loyaltyBlankService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('LOYALTY_BLANK_CREATE', 'LOYALTY_BLANK_FULL')")
    @Operation(summary = "Создание бланка лояльности")
    @SecurityRequirement(name = "Bearer")
    @PostMapping(value = "/")
    public ResponseEntity<ServiceResponse<LoyaltyBlankDTO>> createLoyaltyBlank(
            @Valid
            @RequestBody
                    CreateLoyaltyBlankRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.CREATED.value(), new LoyaltyBlankDTO(loyaltyBlankService.createLoyaltyBlank(request)), "Loyalty Blank created"), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('LOYALTY_BLANK_GET', 'LOYALTY_BLANK_FULL')")
    @Operation(summary = "Получение бланка лояльности по ID")
    @SecurityRequirement(name = "Bearer")
    @GetMapping(value = "/{blankId}")
    public ResponseEntity<ServiceResponse<LoyaltyBlankDTO>> getLoyaltyBlankById(
            @PathVariable UUID blankId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new LoyaltyBlankDTO(loyaltyBlankService.getLoyaltyBlankById(blankId)), "Loyalty Blank returned"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('LOYALTY_BLANK_GET', 'LOYALTY_BLANK_FULL')")
    @Operation(summary = "Получение всех бланков лояльности")
    @SecurityRequirement(name = "Bearer")
    @GetMapping(value = "/")
    public ResponseEntity<ServiceResponse<List<LoyaltyBlankDTO>>> getAllLoyaltyBlanks() {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), loyaltyBlankService.getAllLoyaltyBlanks().stream().map(LoyaltyBlankDTO::new).collect(Collectors.toList()), "Loyalty Blanks returned"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('LOYALTY_BLANK_UPDATE', 'LOYALTY_BLANK_FULL')")
    @Operation(summary = "Изменение бланка лояльности")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping(value = "/{blankId}")
    public ResponseEntity<ServiceResponse<LoyaltyBlankDTO>> updateLoyaltyBlank(
            @Valid
            @RequestBody
                UpdateLoyaltyBlankRequest request,
            @PathVariable
                UUID blankId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new LoyaltyBlankDTO(loyaltyBlankService.updateLoyaltyBlank(request, blankId)), "Loyalty Blank returned"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('LOYALTY_BLANK_DELETE', 'LOYALTY_BLANK_FULL')")
    @Operation(summary = "Удаление бланка лояльности")
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping(value = "/{blankId}")
    public ResponseEntity<ServiceResponse<LoyaltyBlankDTO>> removeLoyaltyBlank(
            @PathVariable
                    UUID blankId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new LoyaltyBlankDTO(loyaltyBlankService.removeLoyaltyBlank(blankId)), "Loyalty Blank returned"), HttpStatus.OK);
    }
}
