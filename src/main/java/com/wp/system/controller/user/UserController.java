package com.wp.system.controller.user;

import com.wp.system.dto.user.UserDTO;
import com.wp.system.entity.user.User;
import com.wp.system.permissions.user.UserPermissions;
import com.wp.system.request.user.CreateUserRequest;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.user.UserService;
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
@SecurityRequirement(name = "Bearer")
public class UserController {
    @Autowired
    private UserService userService;

//    @PreAuthorize("hasAnyAuthority('USER_CREATE', 'USER_FULL')")
    @PostMapping("/")
    public ResponseEntity<ServiceResponse<UserDTO>> createUser(@Valid @RequestBody CreateUserRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.CREATED.value(), new UserDTO(this.userService.createUser(request)), "User created"), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('USER_UPDATE', 'USER_FULL')")
    @PatchMapping("/{userId}")
    public ResponseEntity<ServiceResponse<UserDTO>> updateUser(@RequestBody CreateUserRequest request, @PathVariable UUID userId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserDTO(this.userService.updateUser(request, userId)), "User updated"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER_DELETE', 'USER_FULL')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ServiceResponse<UserDTO>> removeUser(@PathVariable UUID userId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserDTO(this.userService.removeUser(userId)), "User removed"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER_GET', 'USER_FULL')")
    @GetMapping("/{userId}")
    public ResponseEntity<ServiceResponse<UserDTO>> getUserById(@PathVariable UUID userId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserDTO(this.userService.getUserById(userId)), "User returned"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER_GET', 'USER_FULL')")
    @GetMapping("/")
    public ResponseEntity<ServiceResponse<List<UserDTO>>> getAllUsers() {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.userService.getAllUsers().stream().map(UserDTO::new).collect(Collectors.toList()), "Users returned"), HttpStatus.OK);
    }
}
