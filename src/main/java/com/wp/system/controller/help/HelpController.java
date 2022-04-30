package com.wp.system.controller.help;

import com.wp.system.controller.DocumentedRestController;
import com.wp.system.dto.category.CategoryDTO;
import com.wp.system.dto.help.HelpLeadDTO;
import com.wp.system.entity.help.HelpLead;
import com.wp.system.request.category.CreateCategoryRequest;
import com.wp.system.request.category.EditCategoryRequest;
import com.wp.system.request.help.CreateHelpLeadRequest;
import com.wp.system.response.PagingResponse;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.help.HelpService;
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

@RestController
@Tag(name = "Help API")
@RequestMapping("/api/v1/help")
public class HelpController extends DocumentedRestController {
    @Autowired
    private HelpService helpService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Operation(summary = "Создание обращение о помощи")
    @PostMapping("/")
    public ResponseEntity<ServiceResponse<HelpLeadDTO>> createHelpLead(@Valid @RequestBody CreateHelpLeadRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.CREATED.value(), new HelpLeadDTO(this.helpService.createHelpLead(request)), "Help Lead created"), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('HELP_LEAD_DELETE', 'HELP_LEAD_FULL')")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Удаление обращения о помощи")
    @DeleteMapping("/{helpLeadId}")
    public ResponseEntity<ServiceResponse<HelpLeadDTO>> removeHelpLead(@PathVariable UUID helpLeadId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new HelpLeadDTO(this.helpService.removeHelpLead(helpLeadId)), "Help Lead removed"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('HELP_LEAD_GET', 'HELP_LEAD_FULL')")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Получение обращения о помощи по ID")
    @GetMapping("/{helpLeadId}")
    public ResponseEntity<ServiceResponse<HelpLeadDTO>> getHelpLeadById(@PathVariable UUID helpLeadId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new HelpLeadDTO(this.helpService.getHeldLeadById(helpLeadId)), "Help Lead returned"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('HELP_LEAD_GET', 'HELP_LEAD_FULL')")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Получение всех обращений о помощи")
    @GetMapping("/")
    public ResponseEntity<ServiceResponse<List<HelpLeadDTO>>> getHelpLeads() {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.helpService.getAllHelpLeads().stream().map(HelpLeadDTO::new).collect(Collectors.toList()), "Help Leads returned"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('HELP_LEAD_GET', 'HELP_LEAD_FULL')")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Получение всех обращений о помощи постранично")
    @GetMapping("/page")
    public ResponseEntity<ServiceResponse<PagingResponse<HelpLeadDTO>>> getPagedHelpLeads(
            @RequestParam
                    int page,
            @RequestParam
                    int pageSize
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.helpService.getHelpLeadsByPages(page, pageSize), "Help Leads returned"), HttpStatus.OK);
    }
}
