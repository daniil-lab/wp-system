package com.wp.system.controller.logging;

import com.wp.system.dto.logging.SystemAdminLogDTO;
import com.wp.system.dto.logging.SystemErrorLogDTO;
import com.wp.system.request.logging.CreateErrorLogRequest;
import com.wp.system.response.PagingResponse;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.logging.SystemAdminLogger;
import com.wp.system.services.logging.SystemErrorLogger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@Tag(name = "Admin Logging API")
@RequestMapping("/api/v1/admin-log")
@SecurityRequirement(name = "Bearer")
public class SystemAdminLoggingController {
    @Autowired
    private SystemAdminLogger adminLogger;

    //    @PreAuthorize("hasAnyAuthority('ADMIN_LOG_GET', 'ADMIN_LOG_FULL')")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Operation(summary = "Получение всех админ логов")
    @GetMapping("/")
    public ResponseEntity<ServiceResponse<List<SystemAdminLogDTO>>> getAllAdminLogs() {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.adminLogger.getAllAdminLogs().stream().map(SystemAdminLogDTO::new).collect(Collectors.toList()), "Admin logs returned"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADMIN_LOG_GET', 'ADMIN_LOG_FULL')")
    @Operation(summary = "Получение всех админ логов постранично")
    @GetMapping("/page")
    public ResponseEntity<ServiceResponse<PagingResponse<SystemAdminLogDTO>>> getAdminLogsPaged(
            @RequestParam
                    int page,
            @RequestParam
                    int pageSize
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.adminLogger.getPagedAdminLogs(page, pageSize), "Admin logs returned"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADMIN_LOG_GET', 'ADMIN_LOG_FULL')")
    @Operation(summary = "Получение админ лога по ID")
    @GetMapping("/{logId}")
    public ResponseEntity<ServiceResponse<SystemAdminLogDTO>> getAdminLogById(@PathVariable UUID logId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new SystemAdminLogDTO(this.adminLogger.getAdminLogById(logId)), "Admin log returned"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADMIN_LOG_GET', 'ADMIN_LOG_FULL')")
    @Operation(summary = "Получение админ логов по страницам")
    @GetMapping("/user/{userId}")
    public ResponseEntity<ServiceResponse<PagingResponse<SystemAdminLogDTO>>> getAdminLogsByPages(
            @RequestParam
                int pageSize,
            @RequestParam
                int page
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.adminLogger.getPagedAdminLogs(pageSize, page), "Admin logs returned"), HttpStatus.OK);
    }

//    @PreAuthorize("hasAnyAuthority('ERROR_LOG_DELETE', 'ADMIN_LOG_FULL')")
//    @Operation(summary = "Удаление лога ошибки")
//    @DeleteMapping("/{logId}")
//    public ResponseEntity<ServiceResponse<SystemAdminLogDTO>> remove(@PathVariable UUID logId) {
//        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new SystemAdminLogDTO(this.errorLogger.removeErrorLog(logId)), "Admin log removed"), HttpStatus.OK);
//    }
}
