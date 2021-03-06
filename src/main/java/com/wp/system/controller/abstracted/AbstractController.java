package com.wp.system.controller.abstracted;

import com.wp.system.dto.AbstractTransactionDTO;
import com.wp.system.response.PagingResponse;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.abstarct.AbstractService;
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

@RestController
@Tag(name = "Abstract API")
@RequestMapping("/api/v1/abstract")
@SecurityRequirement(name = "Bearer")
public class AbstractController {
    @Autowired
    private AbstractService abstractService;

    @GetMapping("/all-transactions")
    public ResponseEntity<ServiceResponse<PagingResponse<AbstractTransactionDTO>>> getAllTransactions(
            @RequestParam
                    Instant startDate,
            @RequestParam
                    Instant endDate,
            @RequestParam
                    int page,
            @RequestParam
                    int pageSize
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), abstractService.getAllTransactions(
                startDate,
                endDate,
                page,
                pageSize
        )), HttpStatus.OK);
    };
}
