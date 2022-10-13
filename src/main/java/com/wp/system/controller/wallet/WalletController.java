package com.wp.system.controller.wallet;

import com.wp.system.controller.DocumentedRestController;
import com.wp.system.exception.ServiceException;
import com.wp.system.utils.CurrencySingleton;
import com.wp.system.utils.WalletType;
import com.wp.system.response.ServiceResponse;
import com.wp.system.response.wallet.WalletCourseResponse;
import com.wp.system.response.wallet.WalletResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Wallet API")
@RequestMapping("/api/v1/wallet")
public class WalletController extends DocumentedRestController {

    @Autowired
    private CurrencySingleton currencySingleton;

    @Operation(summary = "Получение всех возможных валют в системе")
    @GetMapping("/")
    public ResponseEntity<ServiceResponse<List<WalletResponse>>> getWalletTypes() {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), Arrays.stream(WalletType.values()).map(walletType -> new WalletResponse(walletType.name(), walletType.getWalletName(), walletType.getSymbol())).collect(Collectors.toList())), HttpStatus.OK);
    }

    @Operation(summary = "Получение всех курсов валют по отношению к доллару")
    @GetMapping("/course")
    public ResponseEntity<ServiceResponse<List<WalletCourseResponse>>> getWalletCourses() {
        if(currencySingleton.isError())
            throw new ServiceException("Can`t get Currency Singleton", HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), currencySingleton.getCourseList().stream().map(wallet -> new WalletCourseResponse(wallet.getWallet(), wallet.getCourse())).collect(Collectors.toList())), HttpStatus.OK);
    }
}
