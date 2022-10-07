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
import com.wp.system.request.HideCardRequest;
import com.wp.system.request.sber.CreateSberIntegrationRequest;
import com.wp.system.request.sber.SubmitCreateSberIntegrationRequest;
import com.wp.system.request.sber.UpdateSberTransactionRequest;
import com.wp.system.request.tinkoff.TinkoffStartAuthRequest;
import com.wp.system.request.tinkoff.TinkoffSubmitAuthRequest;
import com.wp.system.request.tinkoff.UpdateTinkoffTransactionRequest;
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

import java.time.Instant;
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

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('SBER_UPDATE', 'SBER_FULL')")
    @PatchMapping(value = "/card/hide")
    @Operation(summary = "Сркрытие карты")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<SberCardDTO>> hideCard(
            @RequestBody
            HideCardRequest request
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), sberService.hideCard(request), ""), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('SBER_UPDATE', 'SBER_FULL')")
    @PatchMapping(value = "/transaction/{transactionId}")
    @Operation(summary = "Обновление транзакции Sber")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<SberTransactionDTO>> updateTransaction(
            @RequestBody
                    UpdateSberTransactionRequest request,
            @PathVariable
                    UUID transactionId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new SberTransactionDTO(sberService.updateSberTransaction(request, transactionId)), ""), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
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
    @CrossOrigin(origins = "*", allowedHeaders = "*")
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
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/sync/")
    @Operation(summary = "Синхронизация Sber")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<Boolean>> syncSber(
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), sberService.syncSber(), ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('SBER_GET', 'SBER_FULL')")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/")
    @Operation(summary = "Получить Sber интеграцию")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<SberIntegrationDTO>> getSberIntegration(
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new SberIntegrationDTO(sberService.getSberIntegrationByUserId()), ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('SBER_GET', 'SBER_FULL')")
    @GetMapping(value = "/cards/")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Operation(summary = "Получить Sber карты")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<List<SberCardDTO>>> getSberCards(
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), sberService.getUserSberCards().stream().map(SberCardDTO::new).collect(Collectors.toList()), ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('SBER_GET', 'SBER_FULL')")
    @GetMapping(value = "/transactions/{cardId}")
    @Operation(summary = "Получить транзакции по карте Sber")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<PagingResponse<SberTransactionDTO>>> getTransactions(
            @PathVariable
                    UUID cardId,
            @RequestParam
                    int page,
            @RequestParam
                    int pageSize,
            @RequestParam
                    Instant startDate,
            @RequestParam
                    Instant endDate
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), sberService.getTransactionsByCardId(cardId, startDate, endDate, page, pageSize), ""), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('SBER_REMOVE', 'SBER_FULL')")
    @DeleteMapping(value = "/")
    @Operation(summary = "Удаление Sber интеграции")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<Boolean>> removeSber(
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), sberService.removeSberIntegration(), ""), HttpStatus.OK);
    }
}
