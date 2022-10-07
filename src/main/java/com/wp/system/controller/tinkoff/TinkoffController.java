package com.wp.system.controller.tinkoff;

import com.wp.system.dto.tinkoff.*;
import com.wp.system.entity.tinkoff.TinkoffCard;
import com.wp.system.entity.tinkoff.TinkoffIntegration;
import com.wp.system.entity.tinkoff.TinkoffSyncStage;
import com.wp.system.entity.tinkoff.TinkoffTransaction;
import com.wp.system.request.HideCardRequest;
import com.wp.system.request.tinkoff.TinkoffStartAuthRequest;
import com.wp.system.request.tinkoff.UpdateTinkoffTransactionRequest;
import com.wp.system.response.PagingResponse;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.acquiring.AcquiringService;
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
import org.springframework.web.servlet.view.RedirectView;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@Tag(name = "Tinkoff API")
@RequestMapping("/api/v1/tinkoff")
@SecurityRequirement(name = "Bearer")
public class TinkoffController {
    @Autowired
    private AcquiringService acquiringService;

    @Autowired
    private TinkoffService tinkoffService;

    @GetMapping("acquiring/results/ok")
    public RedirectView checkPayment(
            @RequestParam(name = "OrderId")
                    String orderId
    ) {
        RedirectView view = new RedirectView();
        if(acquiringService.checkPayment(orderId))
            view.setUrl("https://wallet-box-app.ru/settings?Success=true");
        else
            view.setUrl("https://wallet-box-app.ru/settings?Success=false&message=Ошибка при подключении подписки");

        return view;
    }

    @PreAuthorize("hasAnyAuthority('TINKOFF_CREATE', 'TINKOFF_FULL')")
    @PostMapping(value = "/connect/start")
    @Operation(summary = "Старт авторизации Tinkoff")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<TinkoffAuthRequestDTO>> startAuth(
            @RequestBody
                TinkoffStartAuthRequest request
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new TinkoffAuthRequestDTO(tinkoffService.startTinkoffConnect(request)), ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TINKOFF_UPDATE', 'TINKOFF_FULL')")
    @PatchMapping(value = "/card/hide")
    @Operation(summary = "Скрытие карты")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<TinkoffCardDTO>> hideCard(
            @RequestBody
            HideCardRequest request
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), tinkoffService.hideCard(request), ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TINKOFF_UPDATE', 'TINKOFF_FULL')")
    @PatchMapping(value = "/transaction/{transactionId}")
    @Operation(summary = "Обновление транзакции Tinkoff")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<TinkoffTransactionDTO>> updateTransaction(
            @RequestBody
                    UpdateTinkoffTransactionRequest request,
            @PathVariable
                    UUID transactionId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new TinkoffTransactionDTO(tinkoffService.updateTinkoffTransaction(request, transactionId)), ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TINKOFF_SYNC', 'TINKOFF_FULL')")
    @GetMapping(value = "/sync/")
    @Operation(summary = "Синхронизация")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<Boolean>> syncCards(
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), tinkoffService.sync(), ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TINKOFF_GET', 'TINKOFF_FULL')")
    @GetMapping(value = "/transactions/{cardId}")
    @Operation(summary = "Получить транзакции по карте")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<PagingResponse<TinkoffTransaction>>> getTransactions(
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
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), tinkoffService.getTransactionsByCardId(cardId, page, pageSize, startDate, endDate), ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TINKOFF_GET', 'TINKOFF_FULL')")
    @GetMapping(value = "/")
    @Operation(summary = "Получить интеграцию по ID пользователя")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<TinkoffIntegrationDTO>> getIntegrationByUserId(
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new TinkoffIntegrationDTO(tinkoffService.getIntegrationByUserId()), ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TINKOFF_REMOVE', 'TINKOFF_FULL')")
    @DeleteMapping(value = "/")
    @Operation(summary = "Отключить интеграцию по ID пользователя")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<TinkoffIntegrationDTO>> removeIntegration(
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new TinkoffIntegrationDTO(tinkoffService.removeIntegration()), ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TINKOFF_GET', 'TINKOFF_FULL')")
    @GetMapping(value = "/cards")
    @Operation(summary = "Получение карт")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<Set<TinkoffCardDTO>>> getCards(
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), tinkoffService.getCards().stream().map(TinkoffCardDTO::new).collect(Collectors.toSet()), ""), HttpStatus.OK);
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
