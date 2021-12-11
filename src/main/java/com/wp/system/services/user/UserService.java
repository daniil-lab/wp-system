package com.wp.system.services.user;

import com.wp.system.entity.user.User;
import com.wp.system.entity.user.UserRole;
import com.wp.system.entity.user.UserRolePermission;
import com.wp.system.exception.ServiceException;
import com.wp.system.exception.user.UserErrorCode;
import com.wp.system.permissions.Permission;
import com.wp.system.permissions.PermissionManager;
import com.wp.system.repository.user.UserRepository;
import com.wp.system.repository.user.UserRolePermissionRepository;
import com.wp.system.repository.user.UserRoleRepository;
import com.wp.system.request.user.AddPermissionToRoleRequest;
import com.wp.system.request.user.CreateUserRequest;
import com.wp.system.request.user.CreateUserRoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRolePermissionRepository userRolePermissionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PermissionManager permissionManager;

    public User createUser(CreateUserRequest request) {
        byte[] passwordBytes = Base64.getDecoder().decode(request.getPassword());

        Optional<User> foundUser = this.userRepository.findByUsername(request.getUsername());

        if(foundUser.isPresent())
            throw new ServiceException(UserErrorCode.ALREADY_EXIST);

        UserRole role = null;

        if(request.getRoleName() == null)
            role = this.userRoleService.getAutoApplyRole();
        else
            role = this.userRoleService.getUserRoleByName(request.getRoleName());

        User user = new User(request.getUsername(), passwordEncoder.encode(new String(passwordBytes)));

        user.setRole(role);
        user.setWallet(request.getWalletType());

        userRepository.save(user);

        return user;
    }

    public User getUserByUsername(String username) {
        Optional<User> foundUser = this.userRepository.findByUsername(username);

        if(foundUser.isEmpty())
            throw new ServiceException(UserErrorCode.NOT_FOUND);

        return foundUser.get();
    }

    public List<User> getAllUsers() {
        Iterable<User> foundUsers = this.userRepository.findAll();
        List<User> users = new ArrayList<>();

        foundUsers.forEach(users::add);

        return users;
    }

    public User getUserById(UUID id) {
        Optional<User> foundUser = this.userRepository.findById(id);

        if(foundUser.isEmpty())
            throw new ServiceException(UserErrorCode.NOT_FOUND);

        return foundUser.get();
    }

    public User updateUser(CreateUserRequest request, UUID userId) {
        User user = this.getUserById(userId);

        if(request.getUsername() != null && !request.getUsername().equals(user.getUsername()))
            user.setUsername(request.getUsername());

        if(request.getPassword() != null &&!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            user.setPassword(passwordEncoder.encode(request.getPassword()));

        if(request.getRoleName() != null) {
            UserRole role = this.userRoleService.getUserRoleByName(request.getRoleName());

            user.setRole(role);
        }

        userRepository.save(user);

        return user;
    }

    @Transactional
    public User removeUser(UUID id) {
        User user = this.getUserById(id);

        userRepository.delete(user);

        return user;
    }
}
