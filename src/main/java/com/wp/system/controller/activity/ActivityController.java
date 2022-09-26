package com.wp.system.controller.activity;

import com.wp.system.dto.activity.ActivityDTO;
import com.wp.system.dto.bill.BillDTO;
import com.wp.system.dto.bill.BillTransactionDTO;
import com.wp.system.request.activity.CreateActivityRequest;
import com.wp.system.request.bill.CreateBillRequest;
import com.wp.system.request.bill.DepositBillRequest;
import com.wp.system.request.bill.EditBillRequest;
import com.wp.system.request.bill.WithdrawBillRequest;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.activity.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Activity API")
@RequestMapping("/api/v1/activity")
@SecurityRequirement(name = "Bearer")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ACTIVITY_CREATE', 'ACTIVITY_FULL')")
    @Operation(summary = "Создание активности и прикрепление ее к пользователю")
    @PostMapping("/")
    public ResponseEntity<ServiceResponse<ActivityDTO>> createActivity(
            @Valid
            @RequestBody
                    CreateActivityRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.CREATED.value(), new ActivityDTO(this.activityService.createActivity(request)), "Activity created"), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ACTIVITY_DELETE', 'ACTIVITY_FULL')")
    @Operation(summary = "Удаление активности")
    @DeleteMapping("/{activityId}")
    public ResponseEntity<ServiceResponse<ActivityDTO>> removeActivity(@PathVariable UUID activityId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new ActivityDTO(this.activityService.removeActivity(activityId)), "Activity removed"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ACTIVITY_GET', 'ACTIVITY_FULL')")
    @Operation(summary = "Получение активности по ID")
    @GetMapping("/{activityId}")
    public ResponseEntity<ServiceResponse<ActivityDTO>> getActivityById(@PathVariable UUID activityId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new ActivityDTO(this.activityService.getActivityById(activityId)), "Activity returned"), HttpStatus.OK);
    }
}
