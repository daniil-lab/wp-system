package com.wp.system.controller.system;

import com.wp.system.entity.SystemData;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.system.SystemDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "System Data API")
@RequestMapping("/api/v1/system-data")
public class SystemDataController {
    @Autowired
    private SystemDataService systemDataService;

    @Operation(summary = "Получение объекта системных данных")
    @GetMapping("/")
    public ResponseEntity<ServiceResponse<SystemData>> getSystemData() {
        return new ResponseEntity<>(new ServiceResponse<>(
                HttpStatus.OK.value(),
                systemDataService.getSystemData(),
                "System data returned"
        ), HttpStatus.OK);
    }
}
