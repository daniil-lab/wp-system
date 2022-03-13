package com.wp.system.controller.sber;

import com.wp.system.entity.sber.SberIntegration;
import com.wp.system.entity.tinkoff.TinkoffCard;
import com.wp.system.entity.tinkoff.TinkoffIntegration;
import com.wp.system.entity.tinkoff.TinkoffSyncStage;
import com.wp.system.entity.tinkoff.TinkoffTransaction;
import com.wp.system.request.sber.CreateSberIntegrationRequest;
import com.wp.system.request.sber.SubmitCreateSberIntegrationRequest;
import com.wp.system.request.tinkoff.TinkoffStartAuthRequest;
import com.wp.system.request.tinkoff.TinkoffSubmitAuthRequest;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@Controller
@Tag(name = "Sber API")
@RequestMapping("/api/v1/sber")
@SecurityRequirement(name = "Bearer")
public class SberController {

    @Autowired
    private SberService sberService;

    @PostMapping(value = "/connect/start")
    @Operation(summary = "Старт интеграции Sber")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<Boolean>> startCreateIntegration(
            @RequestBody
                    CreateSberIntegrationRequest request
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), sberService.startCreateSberIntegration(request), ""), HttpStatus.OK);
    }

    @PostMapping(value = "/connect/submit")
    @Operation(summary = "Подтверждение интеграции Sber")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<SberIntegration>> submitCreateIntegartion(
            @RequestBody
                    SubmitCreateSberIntegrationRequest request
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), sberService.submitCreateSberIntegration(request), ""), HttpStatus.OK);
    }

    @GetMapping(value = "/sync/{userId}")
    @Operation(summary = "Синхронизация Sber")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<Boolean>> syncSber(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), sberService.syncSber(userId), ""), HttpStatus.OK);
    }
}
