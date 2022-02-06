package com.wp.system.controller.user;

import com.wp.system.controller.DocumentedRestController;
import com.wp.system.dto.user.UserDTO;
import com.wp.system.entity.user.User;
import com.wp.system.other.user.UserType;
import com.wp.system.permissions.user.UserPermissions;
import com.wp.system.request.user.*;
import com.wp.system.response.PagingResponse;
import com.wp.system.response.ServiceResponse;
import com.wp.system.response.user.UserPageResponse;
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
            @Parameter(required = true, description = """
                        Обязательные поля: username, password, walletType, type.\n
                        Возможные значения walletType и type указаны ниже.\n
                        type - тип пользователя в системе, если пользователь выбрал создание аккаунта через AppleID и т.п,
                        нужно это указать соответствующим элементом.\n
                        walletType - валюта пользователя, которая будет использоваться им.\n
                        Пароль передавать в password, предварительно закодировав его в base64.\n
                        В username передавать номер телефона.\n
                        roleName - название роли, которая должна быть у пользователя. Если не передавать,
                        то система автоматически прикрепит роль к пользователю с полем 'autoApply': true,
                        если такова существует, если не указано и нет такой роли с 'autoApply': true,
                        вернет ошибку.
                        """)
            @RequestBody
                    CreateUserRequest request) {
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
//    @PreAuthorize("hasAnyAuthority('USER_DELETE', 'USER_FULL')")
    @Operation(summary = "Удаление пользователя")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ServiceResponse<UserDTO>> removeUser(@PathVariable UUID userId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserDTO(this.userService.removeUser(userId)), "User removed"), HttpStatus.OK);
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
            @RequestParam(required = false) Instant createAt
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.userService.findUser(phone, email, type, createAt).stream().map(UserDTO::new).collect(Collectors.toList()), "Users returned"), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAnyAuthority('USER_GET', 'USER_FULL')")
    @Operation(summary = "Получение всех пользователей")
    @GetMapping("/")
    public ResponseEntity<ServiceResponse<List<UserDTO>>> getAllUsers() {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.userService.getAllUsers().stream().map(UserDTO::new).collect(Collectors.toList()), "Users returned"), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAnyAuthority('USER_GET', 'USER_FULL')")
    @Operation(summary = "Получение пользователей постранично")
    @GetMapping("/page")
    public ResponseEntity<ServiceResponse<PagingResponse<UserDTO>>> getUsersByPage(
            @RequestParam String page,
            @RequestParam String pageSize
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.userService.getUsersByPage(Integer.parseInt(page), Integer.parseInt(pageSize.replace("/", ""))), "Users returned"), HttpStatus.OK);
    }
}
