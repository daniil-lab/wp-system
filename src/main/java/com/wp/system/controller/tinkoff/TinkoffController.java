package com.wp.system.controller.tinkoff;

import com.wp.system.response.ServiceResponse;
import com.wp.system.services.tinkoff.TinkoffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Tag(name = "Tinkoff API")
@RequestMapping("/api/v1/tinkoff")
@SecurityRequirement(name = "Bearer")
public class TinkoffController {

    @Autowired
    private TinkoffService tinkoffService;

    @GetMapping(value = "/auth-hook")
    @Operation(summary = "Получение данных подписки по ID")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ServiceResponse<Boolean>> authHook(
            @RequestParam
                    String state,
            @RequestParam
                    String code
    ) {
        tinkoffService.authHook(state, code);

        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), true, ""), HttpStatus.OK);
    }
}
