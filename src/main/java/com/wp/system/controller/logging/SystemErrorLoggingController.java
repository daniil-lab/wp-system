package com.wp.system.controller.logging;

import com.wp.system.dto.logging.SystemErrorLogDTO;
import com.wp.system.dto.user.UserRoleDTO;
import com.wp.system.request.logging.CreateErrorLogRequest;
import com.wp.system.request.user.CreateUserRoleRequest;
import com.wp.system.request.user.UpdateUserRoleRequest;
import com.wp.system.response.PagingResponse;
import com.wp.system.response.ServiceResponse;
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
@Tag(name = "Error Logging API")
@RequestMapping("/api/v1/error-log")
@SecurityRequirement(name = "Bearer")
public class SystemErrorLoggingController {
    @Autowired
    private SystemErrorLogger errorLogger;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ERROR_LOG_GET', 'ERROR_LOG_FULL')")
    @Operation(summary = "Получение всех логов ошибок")
    @GetMapping("/")
    public ResponseEntity<ServiceResponse<List<SystemErrorLogDTO>>> getAllErrorLogs() {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.errorLogger.getAllErrorLogs().stream().map(SystemErrorLogDTO::new).collect(Collectors.toList()), "Error logs returned"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ERROR_LOG_GET', 'ERROR_LOG_FULL')")
    @Operation(summary = "Получение лога ошибки по ID")
    @GetMapping("/{logId}")
    public ResponseEntity<ServiceResponse<SystemErrorLogDTO>> getErrorLogById(@PathVariable UUID logId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new SystemErrorLogDTO(this.errorLogger.getErrorLogById(logId)), "Error log returned"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ERROR_LOG_GET', 'ERROR_LOG_FULL')")
    @Operation(summary = "Получение логов ошибок по страницам")
    @GetMapping("/page")
    public ResponseEntity<ServiceResponse<PagingResponse<SystemErrorLogDTO>>> getErrorLogsByPages(
            @RequestParam
                int pageSize,
            @RequestParam
                int page
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.errorLogger.getErrorLogsByPages(pageSize, page), "Error logs returned"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ERROR_LOG_DELETE', 'ERROR_LOG_FULL')")
    @Operation(summary = "Удаление лога ошибки")
    @DeleteMapping("/{logId}")
    public ResponseEntity<ServiceResponse<SystemErrorLogDTO>> removeErrorLog(@PathVariable UUID logId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new SystemErrorLogDTO(this.errorLogger.removeErrorLog(logId)), "Error log removed"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ERROR_LOG_CREATE', 'ERROR_LOG_FULL')")
    @Operation(summary = "Создание лога ошикби")
    @PostMapping("/")
    public ResponseEntity<ServiceResponse<SystemErrorLogDTO>> createErrorLog(@Valid @RequestBody CreateErrorLogRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.CREATED.value(), new SystemErrorLogDTO(this.errorLogger.createErrorLog(request)), "Error log created"), HttpStatus.CREATED);
    }
}
