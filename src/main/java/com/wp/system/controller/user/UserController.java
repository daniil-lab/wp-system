package com.wp.system.controller.user;

import com.wp.system.controller.DocumentedRestController;
import com.wp.system.dto.user.UserDTO;
import com.wp.system.entity.user.User;
import com.wp.system.permissions.user.UserPermissions;
import com.wp.system.request.user.AddUserDeviceTokenRequest;
import com.wp.system.request.user.CreateUserRequest;
import com.wp.system.request.user.EditUserRequest;
import com.wp.system.request.user.SetUserPincodeRequest;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Tag(name = "User API")
@RequestMapping("/api/v1/user")
public class UserController extends DocumentedRestController {
    @Autowired
    private UserService userService;

//    @PreAuthorize("hasAnyAuthority('USER_CREATE', 'USER_FULL')")
    @Operation(summary = "Создание пользователя")
    @PostMapping("/")
    public ResponseEntity<ServiceResponse<UserDTO>> createUser(@Valid @RequestBody CreateUserRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.CREATED.value(), new UserDTO(this.userService.createUser(request)), "User created"), HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAnyAuthority('USER_UPDATE', 'USER_FULL')")
    @Operation(summary = "Изменение пользователя")
    @PatchMapping("/{userId}")
    public ResponseEntity<ServiceResponse<UserDTO>> updateUser(@Valid @RequestBody EditUserRequest request, @PathVariable UUID userId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserDTO(this.userService.updateUser(request, userId)), "User updated"), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAnyAuthority('USER_UPDATE', 'USER_FULL')")
    @Operation(summary = "Чистка данных пользователя")
    @PatchMapping("/clean/{userId}")
    public ResponseEntity<ServiceResponse<UserDTO>> cleanUserData(@PathVariable UUID userId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserDTO(this.userService.cleanUserData(userId)), "User updated"), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAnyAuthority('USER_UPDATE', 'USER_FULL')")
    @Operation(summary = "Установка ПИН кода для пользователя")
    @PatchMapping("/pin")
    public ResponseEntity<ServiceResponse<UserDTO>> setUserPinCode(@Valid @RequestBody SetUserPincodeRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserDTO(this.userService.setUserPincode(request)), "User updated"), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAnyAuthority('USER_UPDATE', 'USER_FULL')")
    @Operation(summary = "Прикрепление токена устройства к пользователю")
    @PatchMapping("/device-token")
    public ResponseEntity<ServiceResponse<UserDTO>> addDeviceTokenToUser(@Valid @RequestBody AddUserDeviceTokenRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserDTO(this.userService.addTokenToUser(request)), "User updated"), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAnyAuthority('USER_UPDATE', 'USER_FULL')")
    @Operation(summary = "Открепление токена устройства к пользователю")
    @DeleteMapping("/device-token/{userId}/{token}")
    public ResponseEntity<ServiceResponse<UserDTO>> removeDeviceTokenFromUser(@PathVariable UUID userId, @PathVariable String token) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserDTO(this.userService.removeDeviceTokenFromUser(userId, token)), "User updated"), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAnyAuthority('USER_UPDATE', 'USER_FULL')")
    @Operation(summary = "Удаление ПИН кода пользователя")
    @DeleteMapping("/pin/{userId}")
    public ResponseEntity<ServiceResponse<UserDTO>> removeUserPinCode(@PathVariable UUID userId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserDTO(this.userService.removeUserPincode(userId)), "User updated"), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAnyAuthority('USER_DELETE', 'USER_FULL')")
    @Operation(summary = "Удаление пользователя")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ServiceResponse<UserDTO>> removeUser(@PathVariable UUID userId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserDTO(this.userService.removeUser(userId)), "User removed"), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAnyAuthority('USER_GET', 'USER_FULL')")
    @Operation(summary = "Получение пользователя по ID")
    @GetMapping("/{userId}")
    public ResponseEntity<ServiceResponse<UserDTO>> getUserById(@PathVariable UUID userId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserDTO(this.userService.getUserById(userId)), "User returned"), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAnyAuthority('USER_GET', 'USER_FULL')")
    @Operation(summary = "Получение всех пользователей")
    @GetMapping("/")
    public ResponseEntity<ServiceResponse<List<UserDTO>>> getAllUsers() {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.userService.getAllUsers().stream().map(UserDTO::new).collect(Collectors.toList()), "Users returned"), HttpStatus.OK);
    }
}
