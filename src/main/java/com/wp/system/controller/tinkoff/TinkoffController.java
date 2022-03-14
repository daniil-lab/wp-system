package com.wp.system.controller.tinkoff;

import com.wp.system.dto.tinkoff.TinkoffAuthChromeTabDTO;
import com.wp.system.dto.tinkoff.TinkoffCardDTO;
import com.wp.system.dto.tinkoff.TinkoffIntegrationDTO;
import com.wp.system.dto.tinkoff.TinkoffTransactionDTO;
import com.wp.system.entity.tinkoff.TinkoffCard;
import com.wp.system.entity.tinkoff.TinkoffIntegration;
import com.wp.system.entity.tinkoff.TinkoffSyncStage;
import com.wp.system.entity.tinkoff.TinkoffTransaction;
import com.wp.system.request.tinkoff.TinkoffStartAuthRequest;
import com.wp.system.response.ServiceResponse;
import com.wp.system.utils.tinkoff.TinkoffAuthChromeTab;
import com.wp.system.request.tinkoff.TinkoffSubmitAuthRequest;
import com.wp.system.services.tinkoff.TinkoffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@Tag(name = "Tinkoff API")
@RequestMapping("/api/v1/tinkoff")
@SecurityRequirement(name = "Bearer")
public class TinkoffController {
    @Autowired
    private TinkoffService tinkoffService;

    @PreAuthorize("hasAnyAuthority('TINKOFF_CREATE', 'TINKOFF_FULL')")
    @PostMapping(value = "/connect/start")
    @Operation(summary = "Старт авторизации Tinkoff")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<TinkoffAuthChromeTabDTO>> startAuth(
            @RequestBody
                TinkoffStartAuthRequest request
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new TinkoffAuthChromeTabDTO(tinkoffService.startTinkoffConnect(request)), ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TINKOFF_SYNC', 'TINKOFF_FULL')")
    @GetMapping(value = "/sync/{userId}")
    @Operation(summary = "Синхронизация")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<Boolean>> syncCards(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), tinkoffService.sync(userId), ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TINKOFF_GET', 'TINKOFF_FULL')")
    @GetMapping(value = "/transactions/{cardId}")
    @Operation(summary = "Получить транзакции по карте")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<Set<TinkoffTransactionDTO>>> getTransactions(
            @PathVariable
                    UUID cardId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), tinkoffService.getTransactionsByCardId(cardId).stream().map(TinkoffTransactionDTO::new).collect(Collectors.toSet()), ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TINKOFF_GET', 'TINKOFF_FULL')")
    @GetMapping(value = "/{userId}")
    @Operation(summary = "Получить интеграцию по ID пользователя")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<TinkoffIntegrationDTO>> getIntegrationByUserId(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new TinkoffIntegrationDTO(tinkoffService.getIntegrationByUserId(userId)), ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TINKOFF_REMOVE', 'TINKOFF_FULL')")
    @DeleteMapping(value = "/{userId}")
    @Operation(summary = "Отключить интеграцию по ID пользователя")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<TinkoffIntegrationDTO>> removeIntegration(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new TinkoffIntegrationDTO(tinkoffService.removeIntegration(userId)), ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TINKOFF_GET', 'TINKOFF_FULL')")
    @GetMapping(value = "/cards/{userId}")
    @Operation(summary = "Получение карт")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<Set<TinkoffCardDTO>>> getCards(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), tinkoffService.getCards(userId).stream().map(TinkoffCardDTO::new).collect(Collectors.toSet()), ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TINKOFF_CREATE', 'TINKOFF_FULL')")
    @PostMapping(value = "/connect/submit")
    @Operation(summary = "Подтвреждение авторизации Tinkoff")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<Boolean>> submitAuth(
            @RequestBody
                    TinkoffSubmitAuthRequest request
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), tinkoffService.submitTinkoffConnect(request), ""), HttpStatus.OK);
    }
}
