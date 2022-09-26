package com.wp.system.controller.system;

import com.wp.system.entity.PublicData;
import com.wp.system.request.system.UpdatePublicDataRequest;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.system.PublicDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Tag(name = "Public Data API")
@RequestMapping("/api/v1/public-data")
public class PublicDataController {
    @Autowired
    private PublicDataService publicDataService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Operation(summary = "Получение объекта публичных данных")
    @GetMapping("/")
    public ResponseEntity<ServiceResponse<PublicData>> getPublicData() {
        return new ResponseEntity<>(new ServiceResponse<>(
                HttpStatus.OK.value(),
                publicDataService.getPublicData(),
                "Public data returned"
        ), HttpStatus.OK);
    }

    @Operation(summary = "Обновление публичных данных")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PatchMapping("/")
    public ResponseEntity<ServiceResponse<Boolean>> updatePublicData(
            @Valid
            @RequestBody
                    UpdatePublicDataRequest request
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), publicDataService.updatePublicData(request), "Public data updated"), HttpStatus.OK);
    }
}
