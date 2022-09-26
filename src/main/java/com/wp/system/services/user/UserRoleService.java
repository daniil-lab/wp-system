package com.wp.system.services.user;

import com.wp.system.entity.user.User;
import com.wp.system.entity.user.UserRole;
import com.wp.system.entity.user.UserRolePermission;
import com.wp.system.exception.ServiceException;
import com.wp.system.permissions.Permission;
import com.wp.system.permissions.PermissionManager;
import com.wp.system.repository.user.UserRolePermissionRepository;
import com.wp.system.repository.user.UserRoleRepository;
import com.wp.system.request.user.AddPermissionToRoleRequest;
import com.wp.system.request.user.CreateUserRoleRequest;
import com.wp.system.request.user.UpdateUserRoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Transactional
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
                    throw new ServiceException("Auto apply role already exist", HttpStatus.BAD_REQUEST);
            } else {
                role.setAutoApply(false);
            }
        }

        if (request.getRoleAfterBuy().isPresent() && role.isRoleAfterBuy() != request.getRoleAfterBuy().get()) {
            if (request.getRoleAfterBuy().get()) {
                Optional<UserRole> userRole = this.userRoleRepository.findByRoleAfterBuy(true);

                if (userRole.isEmpty())
                    role.setRoleAfterBuy(true);
                else
                    throw new ServiceException("After buy role already exist", HttpStatus.BAD_REQUEST);
            } else {
                role.setRoleAfterBuy(false);
            }
        }

        if (request.getRoleForBlocked().isPresent() && role.isRoleForBlocked() != request.getRoleForBlocked().get()) {
            if (request.getRoleForBlocked().get()) {
                Optional<UserRole> userRole = this.userRoleRepository.findByRoleForBlocked(true);

                if (userRole.isEmpty())
                    role.setRoleForBlocked(true);
                else
                    throw new ServiceException("Blocked role already exist", HttpStatus.BAD_REQUEST);
            } else {
                role.setRoleForBlocked(false);
            }
        }

        if (request.getRoleAfterBuyExpiration().isPresent() && role.isRoleAfterBuyExpiration() != request.getRoleAfterBuyExpiration().get()) {
            if (request.getRoleAfterBuyExpiration().get()) {
                Optional<UserRole> userRole = this.userRoleRepository.findByRoleAfterBuyExpiration(true);

                if (userRole.isEmpty())
                    role.setRoleAfterBuyExpiration(true);
                else
                    throw new ServiceException("After buy expiration role already exist", HttpStatus.BAD_REQUEST);
            } else {
                role.setRoleAfterBuyExpiration(false);
            }
        }

        if (request.getIsAdmin().isPresent() && role.isAdmin() != request.getIsAdmin().get())
            role.setAdmin(request.getIsAdmin().get());

        userRoleRepository.save(role);

        return role;
    }

    public UserRole getAutoApplyRole() {
        Optional<UserRole> userRole = this.userRoleRepository.findByAutoApply(true);

        if(userRole.isEmpty())
            throw new ServiceException("Auto apply role not found", HttpStatus.NOT_FOUND);

        return userRole.get();
    }

    public UserRole createUserRole(CreateUserRoleRequest request) {

        if(request.getAutoApply().isPresent() && request.getAutoApply().get()) {
            Optional<UserRole> existRole = this.userRoleRepository.findByAutoApply(true);

            if(existRole.isPresent())
                throw new ServiceException("Auto apply role already exist", HttpStatus.BAD_REQUEST);
        }

        if(request.getRoleAfterBuy().isPresent() && request.getRoleAfterBuy().get()) {
            Optional<UserRole> existRole = this.userRoleRepository.findByRoleAfterBuy(true);

            if(existRole.isPresent())
                throw new ServiceException("After buy role already exist", HttpStatus.BAD_REQUEST);
        }

        if(request.getRoleForBlocked().isPresent() && request.getRoleForBlocked().get()) {
            Optional<UserRole> existRole = this.userRoleRepository.findByRoleForBlocked(true);

            if(existRole.isPresent())
                throw new ServiceException("Blocked role already exist", HttpStatus.BAD_REQUEST);
        }

        if(request.getRoleAfterBuyExpiration().isPresent() && request.getRoleAfterBuyExpiration().get()) {
            Optional<UserRole> existRole = this.userRoleRepository.findByRoleAfterBuyExpiration(true);

            if(existRole.isPresent())
                throw new ServiceException("After buy expiration role already exist", HttpStatus.BAD_REQUEST);
        }

        UserRole userRole = new UserRole(request.getName(), request.getAutoApply().get());

        Optional<UserRole> foundRole = this.userRoleRepository.findByName(request.getName());

        if(foundRole.isPresent())
            throw new ServiceException("Role with given name already exist", HttpStatus.BAD_REQUEST);

        userRole.setAdmin(request.getIsAdmin().get());

        userRole.setAutoApply(request.getAutoApply().get());

        userRole.setRoleAfterBuy(request.getRoleAfterBuy().get());

        userRole.setRoleAfterBuyExpiration(request.getRoleAfterBuyExpiration().get());

        userRoleRepository.save(userRole);

        return userRole;
    }

    public UserRole getUserRoleById(UUID id) {
        Optional<UserRole> userRole = this.userRoleRepository.findById(id);

        if(userRole.isEmpty())
            throw new ServiceException("Role not found", HttpStatus.NOT_FOUND);

        return userRole.get();
    }

    public UserRole getUserRoleByName(String name) {
        Optional<UserRole> userRole = this.userRoleRepository.findByName(name);

        if(userRole.isEmpty())
            throw new ServiceException("Role not found", HttpStatus.NOT_FOUND);

        return userRole.get();
    }
}
