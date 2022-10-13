package com.wp.system.controller.abstracted;

import com.wp.system.dto.AbstractIntegrationDTO;
import com.wp.system.dto.AbstractTransactionDTO;
import com.wp.system.dto.bill.AbstractBillDTO;
import com.wp.system.entity.transaction.Transaction;
import com.wp.system.response.PagingResponse;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.abstarct.AbstractService;
import com.wp.system.utils.bill.TransactionType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Abstract API")
@RequestMapping("/api/v1/abstract")
@SecurityRequirement(name = "Bearer")
public class AbstractController {
    @Autowired
    private AbstractService abstractService;

    @GetMapping("/bills")
    public ResponseEntity<ServiceResponse<List<AbstractBillDTO>>> getBills() {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), abstractService.getBills()), HttpStatus.OK);
    }

    @GetMapping("/integrations")
    public ResponseEntity<ServiceResponse<List<AbstractIntegrationDTO>>> getIntegrations() {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), abstractService.getIntegrations()), HttpStatus.OK);
    }

    @GetMapping("/all-transactions")
    public ResponseEntity<ServiceResponse<PagingResponse<AbstractTransactionDTO>>> getAllTransactions(
            @RequestParam
                    Instant startDate,
            @RequestParam
                    Instant endDate,
            @RequestParam
                    int page,
            @RequestParam
                    int pageSize,
            @RequestParam(required = false)
                TransactionType transactionType,
            @RequestParam(required = false)
                String billType,
            @RequestParam(required = false)
                UUID billId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), abstractService.getAllTransactions(
                startDate,
                endDate,
                transactionType,
                billType,
                billId,
                page,
                pageSize
        )), HttpStatus.OK);
    };
}
