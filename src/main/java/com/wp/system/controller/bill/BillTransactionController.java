package com.wp.system.controller.bill;

import com.wp.system.controller.DocumentedRestController;
import com.wp.system.dto.bill.BillTransactionDTO;
import com.wp.system.request.bill.UpdateBillTransactionRequest;
import com.wp.system.response.PagingResponse;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.bill.BillTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
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

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('BILL_TRANSACTION_GET', 'BILL_TRANSACTION_FULL')")
    @Operation(summary = "Получение всех транзакций за определенный пероид")
    @GetMapping("/period")
    public ResponseEntity<ServiceResponse<PagingResponse<BillTransactionDTO>>> getBillTransactionByDate(@RequestParam Instant start,
                                                                                             @RequestParam Instant end,
                                                                                             @RequestParam Integer page,
                                                                                             @RequestParam Integer pageSize,
                                                                                             @RequestParam(required = false) UUID userId,
                                                                                             @RequestParam(required = false) UUID billId,
                                                                                             @RequestParam(required = false) UUID categoryId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.billTransactionService.getAllTransactionsByPeriod(
                start,
                end,
                page,
                pageSize,
                userId,
                billId,
                categoryId
        ), "Bill Transactions returned"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('BILL_TRANSACTION_GET', 'BILL_TRANSACTION_FULL')")
    @Operation(summary = "Получение всех транзакций счета")
    @GetMapping("/bill/{billId}")
    public ResponseEntity<ServiceResponse<PagingResponse<BillTransactionDTO>>> getAllBillTransactions(@PathVariable UUID billId, @RequestParam int page, @RequestParam int pageSize,
                                                                                                      @RequestParam
                                                                                                              Instant startDate,
                                                                                                      @RequestParam
                                                                                                                  Instant endDate) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.billTransactionService.getAllTransactionsByBillId(billId, page, pageSize, startDate, endDate), "Bill Transactions returned"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('BILL_TRANSACTION_UPDATE', 'BILL_TRANSACTION_FULL')")
    @Operation(summary = "Изменение транзакции")
    @PatchMapping("/{transactionId}")
    public ResponseEntity<ServiceResponse<BillTransactionDTO>> updateTransaction(@PathVariable UUID transactionId, @RequestBody UpdateBillTransactionRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new BillTransactionDTO(this.billTransactionService.updateBillTransaction(request, transactionId)), "Bill Transactions updated"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('BILL_TRANSACTION_UPDATE', 'BILL_TRANSACTION_FULL')")
    @Operation(summary = "Удаление транзакции")
    @DeleteMapping("/{transactionId}")
    public ResponseEntity<ServiceResponse<BillTransactionDTO>> removeTransaction(@PathVariable UUID transactionId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new BillTransactionDTO(this.billTransactionService.removeTransaction(transactionId)), "Bill Transactions removed"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('BILL_TRANSACTION_GET', 'BILL_FULL')")
    @Operation(summary = "Получение всех транзакций пользователя")
    @GetMapping("/user")
    public ResponseEntity<ServiceResponse<PagingResponse<BillTransactionDTO>>> getAllUserTransactions(@RequestParam int page, @RequestParam int pageSize) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.billTransactionService.getAllUserTransactions(page, pageSize), "Bill Transactions returned"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('BILL_TRANSACTION_GET', 'BILL_TRANSACTION_FULL')")
    @Operation(summary = "Получение всех транзакций категории")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ServiceResponse<PagingResponse<BillTransactionDTO>>> getAllCategoryTransactions(@PathVariable UUID categoryId, @RequestParam int page, @RequestParam int pageSize) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.billTransactionService.getAllCategoryTransactions(categoryId, page, pageSize), "Bill Transactions returned"), HttpStatus.OK);
    }
}