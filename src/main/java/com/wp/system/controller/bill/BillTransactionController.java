package com.wp.system.controller.bill;

import com.wp.system.controller.DocumentedRestController;
import com.wp.system.dto.bill.BillDTO;
import com.wp.system.dto.bill.BillTransactionDTO;
import com.wp.system.request.bill.CreateBillRequest;
import com.wp.system.request.bill.DepositBillRequest;
import com.wp.system.request.bill.EditBillRequest;
import com.wp.system.request.bill.WithdrawBillRequest;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.bill.BillService;
import com.wp.system.services.bill.BillTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Transaction API")
@RequestMapping("/api/v1/transaction")
@SecurityRequirement(name = "Bearer")
public class BillTransactionController extends DocumentedRestController {
    @Autowired
    private BillTransactionService billTransactionService;

    @PreAuthorize("hasAnyAuthority('BILL_TRANSACTION_GET', 'BILL_TRANSACTION_FULL')")
    @Operation(summary = "Получение всех транзакций счета")
    @GetMapping("/bill/{billId}")
    public ResponseEntity<ServiceResponse<List<BillTransactionDTO>>> getAllBillTransactions(@PathVariable UUID billId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.billTransactionService.getAllTransactionsByBillId(billId).stream().map(BillTransactionDTO::new).collect(Collectors.toList()), "Bill Transactions returned"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('BILL_TRANSACTION_GET', 'BILL_FULL')")
    @Operation(summary = "Получение всех транзакций пользователя")
    @GetMapping("/user/{userId}")
    public ResponseEntity<ServiceResponse<List<BillTransactionDTO>>> getAllUserTransactions(@PathVariable UUID userId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.billTransactionService.getAllUserTransactions(userId).stream().map(BillTransactionDTO::new).collect(Collectors.toList()), "Bill Transactions returned"), HttpStatus.OK);
    }
}