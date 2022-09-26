package com.wp.system.services.user;

import com.wp.system.dto.permission.PermissionDTO;
import com.wp.system.entity.user.UserRole;
import com.wp.system.entity.user.UserRolePermission;
import com.wp.system.exception.ServiceException;
import com.wp.system.permissions.Permission;
import com.wp.system.permissions.PermissionManager;
import com.wp.system.repository.user.UserRolePermissionRepository;
import com.wp.system.request.user.AddPermissionToRoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserRolePermissionService {

    @Autowired
    private PermissionManager permissionManager;

    @Autowired
    private UserRolePermissionRepository userRolePermissionRepository;

    @Autowired
    private UserRoleService userRoleService;

    public List<UserRolePermission> getAllPermissionsInRole(UUID roleId) {
        return userRolePermissionRepository.getAllPermissionsInRole(roleId);
    }

    public List<PermissionDTO> getAllPermissionVariants() {
        return permissionManager.getPermissionList().stream().map(PermissionDTO::new).collect(Collectors.toList());
    }

    public UserRolePermission getPermissionById(UUID id) {
        Optional<UserRolePermission> foundPermission = this.userRolePermissionRepository.findById(id);

        if(foundPermission.isEmpty())
            throw new ServiceException("Permission not found", HttpStatus.NOT_FOUND);

        return foundPermission.get();
    }

    @Transactional
    public UserRolePermission removePermissionFromRole(UUID permissionId) {
        UserRolePermission userRolePermission = this.getPermissionById(permissionId);

        userRolePermissionRepository.delete(userRolePermission);

        return userRolePermission;
    }

    public UserRolePermission addPermissionToRole(AddPermissionToRoleRequest request, UUID roleId) {
        Permission permission = this.permissionManager.getPermissionBySystemName(request.getSystemName());

        if(permission == null)
            throw new ServiceException("Permission not found", HttpStatus.NOT_FOUND);

        UserRole userRole = this.userRoleService.getUserRoleById(roleId);

        for (UserRolePermission rolePermission : userRole.getPermissions()) {
            if(rolePermission.getPermission().equals(permission.getPermissionSystemValue()))
                throw new ServiceException("Permission already exist", HttpStatus.BAD_REQUEST);
        }

        UserRolePermission userRolePermission = new UserRolePermission(permission, userRole);

        userRolePermissionRepository.save(userRolePermission);

        return userRolePermission;
    }
}
