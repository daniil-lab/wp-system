package com.wp.system.controller.fns;

import com.wp.system.dto.category.CategoryDTO;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.fns.FNSService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "FNS API")
@RequestMapping("/api/v1/fns")
@SecurityRequirement(name = "Bearer")
public class FNSController {
    @Autowired
    private FNSService fnsService;

    @Operation(summary = "Получение информации по чеку")
    @GetMapping("/ticket-info")
    public ResponseEntity<ServiceResponse<String>> getTicketInfo(@RequestParam Double sum,
                                                                 @RequestParam String date,
                                                                 @RequestParam String fn,
                                                                 @RequestParam Integer operationType,
                                                                 @RequestParam Integer fiscalDocumentId,
                                                                 @RequestParam Integer fiscalSign,
                                                                 @RequestParam Boolean rawData) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(),
                fnsService.getTicketInformation(sum,
                        date,
                        fn,
                        operationType,
                        fiscalDocumentId,
                        fiscalSign,
                        rawData), "Ticket info returned"), HttpStatus.OK);
    }

    @Operation(summary = "Получение информации по сообщению")
    @GetMapping("/message")
    public ResponseEntity<ServiceResponse<String>> getTicketInfo(@RequestParam String messageId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(),
                fnsService.getMessageById(messageId), "Message info returned"), HttpStatus.OK);
    }
}
