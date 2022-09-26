package com.wp.system.controller.loyalty;

import com.wp.system.dto.image.SystemImageDTO;
import com.wp.system.dto.loyalty.LoyaltyBlankDTO;
import com.wp.system.dto.loyalty.LoyaltyCardDTO;
import com.wp.system.request.image.UploadImageRequest;
import com.wp.system.request.loyalty.CreateLoyaltyBlankRequest;
import com.wp.system.request.loyalty.CreateLoyaltyCardRequest;
import com.wp.system.request.loyalty.UploadCustomLoyaltyCardImageRequest;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.loyalty.LoyaltyCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.util.function.Tuple2;

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
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Operation(summary = "Создание карты лояльности")
    @SecurityRequirement(name = "Bearer")
    @PostMapping(value = "/")
    public ResponseEntity<ServiceResponse<LoyaltyCardDTO>> createLoyaltyCard(
            @Valid
            @RequestBody
                    CreateLoyaltyCardRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.CREATED.value(), new LoyaltyCardDTO(loyaltyCardService.createLoyaltyCard(request)), "Loyalty Card created"), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('LOYALTY_CARD_UPLOAD_CUSTOM_IMAGE', 'LOYALTY_CARD_FULL')")
    @Operation(summary = "Загрузка кастомной картинки карты")
    @SecurityRequirement(name = "Bearer")
    @PostMapping(value = "/custom-image/{cardId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ServiceResponse<LoyaltyCardDTO>> uploadCustomImage(
            @Valid
            @ModelAttribute
                    UploadCustomLoyaltyCardImageRequest request,
            @PathVariable
                    UUID cardId
            ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.CREATED.value(), new LoyaltyCardDTO(loyaltyCardService.uploadCustomImage(request, cardId)), "Image saved"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('LOYALTY_CARD_REMOVE_CUSTOM_IMAGE', 'LOYALTY_CARD_FULL')")
    @Operation(summary = "Удаление кастомной картинки карты")
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping(value = "/custom-image/{cardId}")
    public ResponseEntity<ServiceResponse<LoyaltyCardDTO>> removeCustomImage(
            @PathVariable
                    UUID cardId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.CREATED.value(), new LoyaltyCardDTO(loyaltyCardService.removeCustomImage(cardId)), "Image removed"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('LOYALTY_CARD_GET_CUSTOM_IMAGE', 'LOYALTY_CARD_FULL')")
    @Operation(summary = "Получение изображения кастомного изображения")
    @GetMapping(value = "/custom-image/{dir}/{file}")
    public ResponseEntity<byte[]> getImageContent(
            @PathVariable
                    String file,
            @PathVariable
                    String dir) {
        Tuple2<byte[], String> data = loyaltyCardService.getImageContent(dir + "/" + file);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf(data.getT2()));

        return ResponseEntity.ok().headers(httpHeaders).body(data.getT1());
    }


    @PreAuthorize("hasAnyAuthority('LOYALTY_CARD_GET', 'LOYALTY_CARD_FULL')")
    @Operation(summary = "Получение карты лояльности по ID")
    @SecurityRequirement(name = "Bearer")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/{cardId}")
    public ResponseEntity<ServiceResponse<LoyaltyCardDTO>> getLoyaltyBlankById(
            @PathVariable UUID cardId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new LoyaltyCardDTO(loyaltyCardService.getLoyaltyCardById(cardId)), "Loyalty Card returned"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('LOYALTY_CARD_GET', 'LOYALTY_CARD_FULL')")
    @Operation(summary = "Получение всех карт лояльности одного пользователя")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @SecurityRequirement(name = "Bearer")
    @GetMapping(value = "/user")
    public ResponseEntity<ServiceResponse<List<LoyaltyCardDTO>>> getAllLoyaltyCards(
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), loyaltyCardService.getAllUserLoyaltyCards().stream().map(LoyaltyCardDTO::new).collect(Collectors.toList()), "Loyalty Cards returned"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('LOYALTY_CARD_DELETE', 'LOYALTY_CARD_FULL')")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Operation(summary = "Удаление карты лояльности")
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping(value = "/{cardId}")
    public ResponseEntity<ServiceResponse<LoyaltyCardDTO>> removeLoyaltyCard(
            @PathVariable UUID cardId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new LoyaltyCardDTO(loyaltyCardService.removeLoyaltyCard(cardId)), "Loyalty Card returned"), HttpStatus.OK);
    }
}
