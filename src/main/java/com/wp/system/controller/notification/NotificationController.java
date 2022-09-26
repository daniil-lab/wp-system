package com.wp.system.controller.notification;

import com.wp.system.request.notification.SendNotificationToAllUserRequest;
import com.wp.system.request.notification.SendNotificationToSomeUsersRequest;
import com.wp.system.request.notification.SendNotificationToUserRequest;
import com.wp.system.response.ServiceResponse;
import com.wp.system.response.notification.SendNotificationResponse;
import com.wp.system.response.notification.SendNotificationToSomeUsersResponse;
import com.wp.system.services.notification.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Tag(name = "Notification API")
@RequestMapping("/api/v1/notification")
@SecurityRequirement(name = "Bearer")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('NOTIFICATION_CREATE', 'NOTIFICATION_FULL')")
    @Operation(summary = "Отправка уведомления одному пользователю")
    @PostMapping("/firebase/user")
    public ResponseEntity<ServiceResponse<SendNotificationResponse>> sendNotificationForUser(@Valid @RequestBody SendNotificationToUserRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.CREATED.value(), this.notificationService.sendNotificationToUser(request), "Notification sent"), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('NOTIFICATION_CREATE', 'NOTIFICATION_FULL')")
    @Operation(summary = "Отправка уведомления нескольким пользователям")
    @PostMapping("/firebase/user/some")
    public ResponseEntity<ServiceResponse<SendNotificationToSomeUsersResponse>> sendNotificationToSomeUsers(@Valid @RequestBody SendNotificationToSomeUsersRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.CREATED.value(), this.notificationService.sendNotificationToSomeUsers(request), "Notifications sent"), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('NOTIFICATION_CREATE', 'NOTIFICATION_FULL')")
    @Operation(summary = "Отправка уведомления всем пользователям")
    @PostMapping("/firebase/user/all")
    public ResponseEntity<ServiceResponse<SendNotificationResponse>> sendNotificationForAllUsers(@Valid @RequestBody SendNotificationToAllUserRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.CREATED.value(), this.notificationService.sendNotificationToAllUsers(request), "Notifications sent"), HttpStatus.CREATED);
    }
}
