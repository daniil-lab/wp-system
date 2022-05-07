package com.wp.system.controller.tochka;

import com.wp.system.dto.tinkoff.TinkoffTransactionDTO;
import com.wp.system.dto.tochka.TochkaCardDTO;
import com.wp.system.dto.tochka.TochkaIntegrationDTO;
import com.wp.system.dto.tochka.TochkaTransactionDTO;
import com.wp.system.entity.tochka.TochkaIntegration;
import com.wp.system.request.tinkoff.UpdateTinkoffTransactionRequest;
import com.wp.system.request.tochka.CreateTochkaIntegrationRequest;
import com.wp.system.request.tochka.UpdateTochkaTransactionRequest;
import com.wp.system.response.PagingResponse;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.tochka.TochkaService;
import com.wp.system.utils.tochka.TochkaAuthSubmit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.naming.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Tochka API")
@RequestMapping("/api/v1/tochka")
@SecurityRequirement(name = "Bearer")
public class TochkaController {
    @Autowired
    private TochkaService tochkaService;

    @GetMapping("/auth-hook")
    public ResponseEntity authHook(
            @RequestParam
                    String code
    ) {
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('TOCHKA_UPDATE', 'TOCHKA_FULL')")
    @GetMapping(value = "/transactions/{cardId}")
    @Operation(summary = "Получить транзакции по карте")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<PagingResponse<TochkaTransactionDTO>>> getTransactions(
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
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), tochkaService.getTransactionsByCardId(cardId, startDate, endDate, page, pageSize), ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TOCHKA_UPDATE', 'TOCHKA_FULL')")
    @PatchMapping(value = "/transaction/{transactionId}")
    @Operation(summary = "Обновление транзакции Tochka")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<TochkaTransactionDTO>> updateTransaction(
            @RequestBody
                    UpdateTochkaTransactionRequest request,
            @PathVariable
                    UUID transactionId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new TochkaTransactionDTO(tochkaService.updateTochkaTransaction(request, transactionId)), ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TOCHKA_CREATE', 'TOCHKA_FULL')")
    @PostMapping("/submit-auth")
    public ResponseEntity<ServiceResponse<TochkaIntegrationDTO>> submitAuth(
            @RequestBody
            @Valid
                    CreateTochkaIntegrationRequest request
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new TochkaIntegrationDTO(tochkaService.submitCreate(request))), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TOCHKA_SYNC', 'TOCHKA_FULL')")
    @GetMapping("/sync/")
    public ResponseEntity<ServiceResponse<Boolean>> sync(
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), tochkaService.sync()), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TOCHKA_GET', 'TOCHKA_FULL')")
    @GetMapping("/cards")
    public ResponseEntity<ServiceResponse<List<TochkaCardDTO>>> getCards(
    ) {
        return new ResponseEntity<>(new ServiceResponse(HttpStatus.OK.value(), tochkaService.getCards().stream().map(TochkaCardDTO::new).collect(Collectors.toList())), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TOCHKA_GET', 'TOCHKA_FULL')")
    @GetMapping("/")
    public ResponseEntity<ServiceResponse<TochkaIntegrationDTO>> getIntegration(
    ) {
        return new ResponseEntity<>(new ServiceResponse(HttpStatus.OK.value(), tochkaService.getIntegration()), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TOCHKA_DELETE', 'TOCHKA_FULL')")
    @DeleteMapping("/")
    public ResponseEntity<ServiceResponse<TochkaIntegrationDTO>> removeIntegraiton(
    ) {
        return new ResponseEntity<>(new ServiceResponse(HttpStatus.OK.value(), tochkaService.removeIntegration()), HttpStatus.OK);
    }
}
