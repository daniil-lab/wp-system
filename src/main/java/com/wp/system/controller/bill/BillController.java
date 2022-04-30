package com.wp.system.controller.bill;

import com.wp.system.controller.DocumentedRestController;
import com.wp.system.dto.bill.BillDTO;
import com.wp.system.dto.bill.BillTransactionDTO;
import com.wp.system.dto.category.CategoryDTO;
import com.wp.system.request.bill.CreateBillRequest;
import com.wp.system.request.bill.DepositBillRequest;
import com.wp.system.request.bill.EditBillRequest;
import com.wp.system.request.bill.WithdrawBillRequest;
import com.wp.system.request.category.CreateCategoryRequest;
import com.wp.system.request.category.EditCategoryRequest;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.bill.BillService;
import com.wp.system.services.category.CategoryService;
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
@Tag(name = "Bill API")
@RequestMapping("/api/v1/bill")
@SecurityRequirement(name = "Bearer")
public class BillController extends DocumentedRestController {
    @Autowired
    private BillService billService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('BILL_CREATE', 'BILL_FULL')")
    @Operation(summary = "Создание счета и прикрепление ее к пользователю")
    @PostMapping("/")
    public ResponseEntity<ServiceResponse<BillDTO>> createBill(
            @Valid
            @RequestBody
                    CreateBillRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.CREATED.value(), new BillDTO(this.billService.createBill(request)), "Bill created"), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('BILL_UPDATE', 'BILL_FULL')")
    @Operation(summary = "Изменение счета")
    @PatchMapping("/{billId}")
    public ResponseEntity<ServiceResponse<BillDTO>> updateBill(@Valid @RequestBody EditBillRequest request, @PathVariable UUID billId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new BillDTO(this.billService.updateBill(request, billId)), "Bill updated"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('BILL_UPDATE', 'BILL_FULL')")
    @Operation(summary = "Пополнение счета")
    @PatchMapping("/deposit/{billId}")
    public ResponseEntity<ServiceResponse<BillTransactionDTO>> depositBill(@Valid @RequestBody DepositBillRequest request, @PathVariable UUID billId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new BillTransactionDTO(this.billService.depositBill(request, billId)), "Bill updated"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('BILL_UPDATE', 'BILL_FULL')")
    @Operation(summary = "Снятие счета")
    @PatchMapping("/withdraw/{billId}")
    public ResponseEntity<ServiceResponse<BillTransactionDTO>> withdrawBill(@Valid @RequestBody WithdrawBillRequest request, @PathVariable UUID billId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new BillTransactionDTO(this.billService.withdrawBill(request, billId)), "Bill updated"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('BILL_GET', 'BILL_FULL')")
    @Operation(summary = "Удаление счета")
    @DeleteMapping("/{billId}")
    public ResponseEntity<ServiceResponse<BillDTO>> removeBill(@PathVariable UUID billId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new BillDTO(this.billService.removeBill(billId)), "Bill removed"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('BILL_GET', 'BILL_FULL')")
    @Operation(summary = "Получение счета по ID")
    @GetMapping("/{billId}")
    public ResponseEntity<ServiceResponse<BillDTO>> getBillById(@PathVariable UUID billId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new BillDTO(this.billService.getBillById(billId)), "Bill returned"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('BILL_GET', 'BILL_FULL')")
    @Operation(summary = "Получение всех счетов пользователя")
    @GetMapping("/")
    public ResponseEntity<ServiceResponse<List<BillDTO>>> getUserBills() {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.billService.getUserBills().stream().map(BillDTO::new).collect(Collectors.toList()), "Bills returned"), HttpStatus.OK);
    }
}
