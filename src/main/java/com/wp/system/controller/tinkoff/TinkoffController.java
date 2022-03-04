package com.wp.system.controller.tinkoff;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@Controller
@Tag(name = "Tinkoff API")
@RequestMapping("/api/v1/tinkoff")
@SecurityRequirement(name = "Bearer")
public class TinkoffController {

    @Autowired
    private TinkoffService tinkoffService;

    @PostMapping(value = "/connect/start")
    @Operation(summary = "Старт авторизации Tinkoff")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<TinkoffAuthChromeTab>> startAuth(
            @RequestBody
                TinkoffStartAuthRequest request
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), tinkoffService.startTinkoffConnect(request), ""), HttpStatus.OK);
    }

    @GetMapping(value = "/sync/{userId}")
    @Operation(summary = "Синхронизация")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<Boolean>> syncCards(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), tinkoffService.sync(userId), ""), HttpStatus.OK);
    }

    @GetMapping(value = "/transactions/{cardId}")
    @Operation(summary = "Получить транзакции по карте")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<Set<TinkoffTransaction>>> getTransactions(
            @PathVariable
                    UUID cardId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), tinkoffService.getTransactionsByCardId(cardId), ""), HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}")
    @Operation(summary = "Получить интеграцию по ID пользователя")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<TinkoffIntegration>> getIntegrationByUserId(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), tinkoffService.getIntegrationByUserId(userId), ""), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{userId}")
    @Operation(summary = "Отключить интеграцию по ID пользователя")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<TinkoffIntegration>> removeIntegration(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), tinkoffService.removeIntegration(userId), ""), HttpStatus.OK);
    }

    @GetMapping(value = "/sync/status/{userId}")
    @Operation(summary = "Синхронизация карт")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<TinkoffSyncStage>> syncStatus(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), tinkoffService.getSyncStage(userId), ""), HttpStatus.OK);
    }

    @GetMapping(value = "/cards/{userId}")
    @Operation(summary = "Получение карт")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<Set<TinkoffCard>>> getCards(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), tinkoffService.getCards(userId), ""), HttpStatus.OK);
    }

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
