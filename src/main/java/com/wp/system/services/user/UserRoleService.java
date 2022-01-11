package com.wp.system.services.user;

import com.wp.system.entity.user.User;
import com.wp.system.entity.user.UserRole;
import com.wp.system.entity.user.UserRolePermission;
import com.wp.system.exception.ServiceException;
import com.wp.system.exception.user.UserErrorCode;
import com.wp.system.permissions.Permission;
import com.wp.system.permissions.PermissionManager;
import com.wp.system.repository.user.UserRolePermissionRepository;
import com.wp.system.repository.user.UserRoleRepository;
import com.wp.system.request.user.AddPermissionToRoleRequest;
import com.wp.system.request.user.CreateUserRoleRequest;
import com.wp.system.request.user.UpdateUserRoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserRoleService {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRolePermissionRepository userRolePermissionRepository;

    @Autowired
    private PermissionManager permissionManager;

    public UserRole getUserRoleByUserId(UUID id) {
        User user = userService.getUserById(id);

        return user.getRole();
    }

    public List<UserRole> getAllUserRoles() {
        Iterable<UserRole> foundRoles = this.userRoleRepository.findAll();
        List<UserRole> roles = new ArrayList<>();

        foundRoles.forEach(roles::add);

        return roles;
    }

    @Transactional
    public UserRole removeUserRole(UUID roleId) {
        UserRole role = this.getUserRoleById(roleId);

        userRoleRepository.delete(role);

        return role;
    }

    public UserRole updateUserRole(UpdateUserRoleRequest request, UUID roleId) {
        UserRole role = this.getUserRoleById(roleId);

        if (request.getName() != null && !role.getName().equals(request.getName()))
            role.setName(request.getName());

        if (request.getAutoApply().isPresent() && role.isAutoApply() != request.getAutoApply().get()) {
            if (request.getAutoApply().get()) {
                Optional<UserRole> userRole = this.userRoleRepository.findByAutoApply(true);

                if (userRole.isEmpty())
                    role.setAutoApply(true);
                else
                    throw new ServiceException(UserErrorCode.AUTOAPPLY_ROLE_ALREADY_EXIST);
            } else {
                role.setAutoApply(request.getAutoApply().get());
            }
        }

        if (request.getIsAdmin().isPresent() && role.isAutoApply() != request.getAutoApply().get())
            role.setAdmin(request.getIsAdmin().get());

        userRoleRepository.save(role);

        return role;
    }

    public UserRole getAutoApplyRole() {
        Optional<UserRole> userRole = this.userRoleRepository.findByAutoApply(true);

        if(userRole.isEmpty())
            throw new ServiceException(UserErrorCode.AUTOAPPLY_ROLE_NOT_FOUND);

        return userRole.get();
    }

    public UserRole createUserRole(CreateUserRoleRequest request) {

        if(request.getAutoApply().get()) {
            Optional<UserRole> existRole = this.userRoleRepository.findByAutoApply(true);

            if(existRole.isPresent())
                throw new ServiceException(UserErrorCode.AUTOAPPLY_ROLE_ALREADY_EXIST);
        }

        UserRole userRole = new UserRole(request.getName(), request.getAutoApply().get());

        Optional<UserRole> foundRole = this.userRoleRepository.findByName(request.getName());

        if(foundRole.isPresent())
            throw new ServiceException(UserErrorCode.ROLE_ALREADY_EXIST);

        userRole.setAdmin(request.getIsAdmin().get());

        userRoleRepository.save(userRole);

        return userRole;
    }

    public UserRole getUserRoleById(UUID id) {
        Optional<UserRole> userRole = this.userRoleRepository.findById(id);

        if(userRole.isEmpty())
            throw new ServiceException(UserErrorCode.ROLE_NOT_FOUND);

        return userRole.get();
    }

    public UserRole getUserRoleByName(String name) {
        Optional<UserRole> userRole = this.userRoleRepository.findByName(name);

        if(userRole.isEmpty())
            throw new ServiceException(UserErrorCode.ROLE_NOT_FOUND);

        return userRole.get();
    }
}
