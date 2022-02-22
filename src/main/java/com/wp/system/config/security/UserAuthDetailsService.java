package com.wp.system.config.security;

import com.wp.system.entity.user.User;
import com.wp.system.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class UserAuthDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserAuthDetails loadUserByUsername(String username) {
        User user = userService.getUserByUsername(username);

        return UserAuthDetails.createUserAuthDetails(user);
    }

    public UserAuthDetails loadByEmail(String email) {
        User user = userService.getUserByEmail(email);

        return UserAuthDetails.createUserAuthDetails(user);
    }
}