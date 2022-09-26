package com.wp.system.controller.user;

import com.wp.system.controller.DocumentedRestController;
import com.wp.system.dto.permission.PermissionDTO;
import com.wp.system.entity.user.User;
import com.wp.system.entity.user.UserRole;
import com.wp.system.entity.user.UserRolePermission;
import com.wp.system.permissions.Permission;
import com.wp.system.request.user.AddPermissionToRoleRequest;
import com.wp.system.request.user.CreateUserRequest;
import com.wp.system.request.user.CreateUserRoleRequest;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.user.UserRolePermissionService;
import com.wp.system.services.user.UserRoleService;
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

@RestController
@Tag(name = "User Role Permission API")
@RequestMapping("/api/v1/user-role/permission")
@SecurityRequirement(name = "Bearer")
public class UserRolePermissionController extends DocumentedRestController {

    @Autowired
    private UserRolePermissionService userRolePermissionService;

    @PreAuthorize("hasAnyAuthority('USER_ROLE_PERMISSION_GET', 'USER_ROLE_PERMISSION_FULL')")
    @Operation(summary = "Получение всех возможных доступов в системе")
    @GetMapping("/variants")
    public ResponseEntity<ServiceResponse<List<PermissionDTO>>> getAllPermissionsVariants() {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.userRolePermissionService.getAllPermissionVariants(), "User Role Permissions returned"), HttpStatus.OK);
    }

//    @PreAuthorize("hasAnyAuthority('USER_ROLE_PERMISSION_CREATE', 'USER_ROLE_PERMISSION_FULL')")
    @Operation(summary = "Добавление доступа к роли")
    @PostMapping("/{roleId}")
    public ResponseEntity<ServiceResponse<UserRolePermission>> addPermissionToUserRole(@Valid @RequestBody AddPermissionToRoleRequest request, @PathVariable UUID roleId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.userRolePermissionService.addPermissionToRole(request, roleId), "User Role Permission added to User Role"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE_PERMISSION_GET', 'USER_ROLE_PERMISSION_FULL')")
    @Operation(summary = "Получение прикрепленного доступа по ID")
    @GetMapping("/{permissionId}")
    public ResponseEntity<ServiceResponse<UserRolePermission>> getPermissionById(@PathVariable UUID permissionId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.userRolePermissionService.getPermissionById(permissionId), "User Role Permission returns"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE_PERMISSION_GET', 'USER_ROLE_PERMISSION_FULL')")
    @Operation(summary = "Получение прикрепленных доступов к определенной роли с указанным ID")
    @GetMapping("/role/{roleId}")
    public ResponseEntity<ServiceResponse<List<UserRolePermission>>> getAllPermissionsInRole(@PathVariable UUID roleId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.userRolePermissionService.getAllPermissionsInRole(roleId), "User Role Permissions returns"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE_PERMISSION_DELETE', 'USER_ROLE_PERMISSION_FULL')")
    @Operation(summary = "Удаление прикрепленного доступа")
    @DeleteMapping("/{permissionId}")
    public ResponseEntity<ServiceResponse<UserRolePermission>> removePermission(@PathVariable UUID permissionId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.userRolePermissionService.removePermissionFromRole(permissionId), "User Role Permission returns"), HttpStatus.OK);
    }
}
