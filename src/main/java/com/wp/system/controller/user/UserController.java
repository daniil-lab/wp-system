package com.wp.system.controller.user;

import com.wp.system.controller.DocumentedRestController;
import com.wp.system.dto.user.UserDTO;
import com.wp.system.utils.user.UserType;
import com.wp.system.request.user.*;
import com.wp.system.response.PagingResponse;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Instant;
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
    public ResponseEntity<ServiceResponse<UserDTO>> createUser(
            @Valid
            @Parameter(required = true)
            @RequestBody
                    CreateUserRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.CREATED.value(), new UserDTO(this.userService.createUser(request)), "User created"), HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAnyAuthority('USER_UPDATE', 'USER_FULL')")
    @Operation(summary = "Изменение планов пользователя")
    @PatchMapping("/plans")
    public ResponseEntity<ServiceResponse<UserDTO>> editUserPlans(@Valid @RequestBody EditPlansRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserDTO(this.userService.updatePlans(request)), "User updated"), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAnyAuthority('USER_UPDATE', 'USER_FULL')")
    @Operation(summary = "Изменение пользователя")
    @PatchMapping("/")
    public ResponseEntity<ServiceResponse<UserDTO>> updateUser(@Valid @RequestBody EditUserRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserDTO(this.userService.updateUser(request)), "User updated"), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAnyAuthority('USER_UPDATE', 'USER_FULL')")
    @Operation(summary = "Линк пользователя с Гуглом")
    @PatchMapping("/google-link")
    public ResponseEntity<ServiceResponse<UserDTO>> googleLink(@Valid @RequestBody LinkWithGoogleRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserDTO(this.userService.linkWithGoogle(request)), "User updated"), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAnyAuthority('USER_UPDATE', 'USER_FULL')")
    @Operation(summary = "Удаление линка пользователя с Гуглом")
    @DeleteMapping("/google-link")
    public ResponseEntity<ServiceResponse<UserDTO>> removeGoogleLink() {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserDTO(this.userService.removeLinkWithGoogle()), "User updated"), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAnyAuthority('USER_UPDATE', 'USER_FULL')")
    @Operation(summary = "Чистка данных пользователя")
    @PatchMapping("/clean")
    public ResponseEntity<ServiceResponse<UserDTO>> cleanUserData(@Valid @RequestBody CleanUserRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserDTO(this.userService.cleanUserData(request)), "User updated"), HttpStatus.OK);
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
    @DeleteMapping("/device-token/{token}")
    public ResponseEntity<ServiceResponse<UserDTO>> removeDeviceTokenFromUser( @PathVariable String token) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserDTO(this.userService.removeDeviceTokenFromUser(token)), "User updated"), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAnyAuthority('USER_UPDATE', 'USER_FULL')")
    @Operation(summary = "Удаление ПИН кода пользователя")
    @DeleteMapping("/pin")
    public ResponseEntity<ServiceResponse<UserDTO>> removeUserPinCode() {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserDTO(this.userService.removeUserPincode()), "User updated"), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer")
//    @PreAuthorize("hasAnyAuthority('USER_DELETE', 'USER_FULL')")
    @Operation(summary = "Удаление пользователя")
    @DeleteMapping("/")
    public ResponseEntity<ServiceResponse<UserDTO>> removeUser() {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserDTO(this.userService.removeUser()), "User removed"), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAnyAuthority('USER_GET', 'USER_FULL')")
    @Operation(summary = "Экспорт данных пользователя за период в CSV (пока нет EMAIL сервиса, качается файлом)")
    @PostMapping("/export")
    public ResponseEntity<InputStreamResource> exportUserData (@Valid @RequestBody ExportDataRequest request) throws FileNotFoundException {
        File file = this.userService.exportCSVData(request);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();

        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
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
    @Operation(summary = "Поиск пользователей")
    @GetMapping("/find")
    public ResponseEntity<ServiceResponse<List<UserDTO>>> findUsers(
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) UserType type,
            @RequestParam(required = false) Instant startDate,
            @RequestParam(required = false) Instant endDate
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.userService.findUser(phone, email, type, startDate, endDate).stream().map(UserDTO::new).collect(Collectors.toList()), "Users returned"), HttpStatus.OK);
    }
}
