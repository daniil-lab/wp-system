package com.wp.system.controller.user;

import com.wp.system.dto.user.UserDTO;
import com.wp.system.dto.user.UserRoleDTO;
import com.wp.system.entity.user.User;
import com.wp.system.entity.user.UserRole;
import com.wp.system.request.user.CreateUserRequest;
import com.wp.system.request.user.CreateUserRoleRequest;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.user.UserRoleService;
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
@Tag(name = "User Role API")
@RequestMapping("/api/v1/user-role")
@SecurityRequirement(name = "Bearer")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;

    @PreAuthorize("hasAnyAuthority('USER_ROLE_GET', 'USER_ROLE_FULL')")
    @GetMapping("/")
    public ResponseEntity<ServiceResponse<List<UserRoleDTO>>> getAllUserRoles() {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.userRoleService.getAllUserRoles().stream().map(UserRoleDTO::new).collect(Collectors.toList()), "User Roles returned"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE_GET', 'USER_ROLE_FULL')")
    @GetMapping("/{roleId}")
    public ResponseEntity<ServiceResponse<UserRoleDTO>> getUserRoleById(@PathVariable UUID roleId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserRoleDTO(this.userRoleService.getUserRoleById(roleId)), "User Role returned"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE_UPDATE', 'USER_ROLE_FULL')")
    @PatchMapping("/{roleId}")
    public ResponseEntity<ServiceResponse<UserRoleDTO>> updateUserRole(@RequestBody CreateUserRoleRequest request, @PathVariable UUID roleId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserRoleDTO(this.userRoleService.updateUserRole(request, roleId)), "User Role updated"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE_DELETE', 'USER_ROLE_FULL')")
    @DeleteMapping("/{roleId}")
    public ResponseEntity<ServiceResponse<UserRoleDTO>> removeUserRole(@PathVariable UUID roleId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserRoleDTO(this.userRoleService.removeUserRole(roleId)), "User Role removed"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE_CREATE', 'USER_ROLE_FULL')")
    @PostMapping("/")
    public ResponseEntity<ServiceResponse<UserRoleDTO>> createUserRole(@Valid @RequestBody CreateUserRoleRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.CREATED.value(), new UserRoleDTO(this.userRoleService.createUserRole(request)), "User Role created"), HttpStatus.CREATED);
    }
}
