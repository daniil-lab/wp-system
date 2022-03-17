package com.wp.system.controller.sber;

import com.wp.system.dto.sber.SberCardDTO;
import com.wp.system.dto.sber.SberIntegrationDTO;
import com.wp.system.dto.sber.SberTransactionDTO;
import com.wp.system.dto.tinkoff.TinkoffTransactionDTO;
import com.wp.system.entity.sber.SberIntegration;
import com.wp.system.entity.tinkoff.TinkoffCard;
import com.wp.system.entity.tinkoff.TinkoffIntegration;
import com.wp.system.entity.tinkoff.TinkoffSyncStage;
import com.wp.system.entity.tinkoff.TinkoffTransaction;
import com.wp.system.request.sber.CreateSberIntegrationRequest;
import com.wp.system.request.sber.SubmitCreateSberIntegrationRequest;
import com.wp.system.request.tinkoff.TinkoffStartAuthRequest;
import com.wp.system.request.tinkoff.TinkoffSubmitAuthRequest;
import com.wp.system.response.PagingResponse;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.sber.SberService;
import com.wp.system.services.tinkoff.TinkoffService;
import com.wp.system.utils.tinkoff.TinkoffAuthChromeTab;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@Tag(name = "Sber API")
@RequestMapping("/api/v1/sber")
@SecurityRequirement(name = "Bearer")
public class SberController {
    @Autowired
    private SberService sberService;

    @PreAuthorize("hasAnyAuthority('SBER_CREATE', 'SBER_FULL')")
    @PostMapping(value = "/connect/start")
    @Operation(summary = "Старт интеграции Sber")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<Boolean>> startCreateIntegration(
            @RequestBody
                    CreateSberIntegrationRequest request
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), sberService.startCreateSberIntegration(request), ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('SBER_CREATE', 'SBER_FULL')")
    @PostMapping(value = "/connect/submit")
    @Operation(summary = "Подтверждение интеграции Sber")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<SberIntegrationDTO>> submitCreateIntegartion(
            @RequestBody
                    SubmitCreateSberIntegrationRequest request
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new SberIntegrationDTO(sberService.submitCreateSberIntegration(request)), ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('SBER_SYNC', 'SBER_FULL')")
    @GetMapping(value = "/sync/{userId}")
    @Operation(summary = "Синхронизация Sber")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<Boolean>> syncSber(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), sberService.syncSber(userId), ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('SBER_GET', 'SBER_FULL')")
    @GetMapping(value = "/{userId}")
    @Operation(summary = "Получить Sber интеграцию")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<SberIntegrationDTO>> getSberIntegration(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new SberIntegrationDTO(sberService.getSberIntegrationByUserId(userId)), ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('SBER_GET', 'SBER_FULL')")
    @GetMapping(value = "/cards/{userId}")
    @Operation(summary = "Получить Sber карты")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<List<SberCardDTO>>> getSberCards(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), sberService.getUserSberCards(userId).stream().map(SberCardDTO::new).collect(Collectors.toList()), ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('SBER_GET', 'SBER_FULL')")
    @GetMapping(value = "/transactions/{cardId}")
    @Operation(summary = "Получить транзакции по карте Sber")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<PagingResponse<SberTransactionDTO>>> getTransactions(
            @PathVariable
                    UUID cardId,
            @RequestParam
                    int page,
            @RequestParam
                    int pageSize
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), sberService.getTransactionsByCardId(cardId, page, pageSize), ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('SBER_REMOVE', 'SBER_FULL')")
    @DeleteMapping(value = "/{userId}")
    @Operation(summary = "Удаление Sber интеграции")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<Boolean>> removeSber(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), sberService.removeSberIntegration(userId), ""), HttpStatus.OK);
    }
}
