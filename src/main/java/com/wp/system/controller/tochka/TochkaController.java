package com.wp.system.controller.tochka;

import com.wp.system.dto.tochka.TochkaCardDTO;
import com.wp.system.dto.tochka.TochkaIntegrationDTO;
import com.wp.system.entity.tochka.TochkaIntegration;
import com.wp.system.request.tochka.CreateTochkaIntegrationRequest;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.tochka.TochkaService;
import com.wp.system.utils.tochka.TochkaAuthSubmit;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.naming.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Tochka API")
@RequestMapping("/api/v1/tochka")
@SecurityRequirement(name = "Bearer")
public class TochkaController {
    @Autowired
    private TochkaService tochkaService;

    @GetMapping("/auth-hook")
    public ResponseEntity authHook(
            @RequestParam
                    String code
    ) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/submit-auth")
    public ResponseEntity<ServiceResponse<TochkaIntegrationDTO>> submitAuth(
            @RequestBody
            @Valid
                    CreateTochkaIntegrationRequest request
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new TochkaIntegrationDTO(tochkaService.submitCreate(request))), HttpStatus.OK);
    }

    @GetMapping("/sync/{userId}")
    public ResponseEntity<ServiceResponse<Boolean>> sync(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), tochkaService.sync(userId)), HttpStatus.OK);
    }

    @GetMapping("/cards/{userId}")
    public ResponseEntity<ServiceResponse<List<TochkaCardDTO>>> getCards(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse(HttpStatus.OK.value(), tochkaService.getCards(userId).stream().map(TochkaCardDTO::new).collect(Collectors.toList())), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ServiceResponse<TochkaIntegrationDTO>> getIntegration(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse(HttpStatus.OK.value(), tochkaService.getIntegration(userId)), HttpStatus.OK);
    }
}
