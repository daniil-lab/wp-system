package com.wp.system.controller.wallet;

import com.wp.system.controller.DocumentedRestController;
import com.wp.system.dto.permission.PermissionDTO;
import com.wp.system.other.WalletType;
import com.wp.system.response.ServiceResponse;
import com.wp.system.response.wallet.WalletResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@Tag(name = "Wallet API")
@RequestMapping("/api/v1/wallet")
public class WalletController extends DocumentedRestController {

    @Operation(summary = "Получение всех возможных валют в системе")
    @GetMapping("/")
    public ResponseEntity<ServiceResponse<List<WalletResponse>>> getWalletTypes() {
        List<WalletResponse> responseData = new ArrayList<>();

        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), Arrays.stream(WalletType.values()).map(walletType -> new WalletResponse(walletType.name(), walletType.getWalletName())).toList()), HttpStatus.OK);
    }
}
