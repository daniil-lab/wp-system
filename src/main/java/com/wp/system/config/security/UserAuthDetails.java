package com.wp.system.config.security;

import com.wp.system.entity.user.User;
import com.wp.system.entity.user.UserRole;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

public class UserAuthDetails implements UserDetails {
    private String username;

    private String password;

    private UserRole role;

    private UUID id;

    private boolean admin;

    public static UserAuthDetails createUserAuthDetails(User user) {
        UserAuthDetails c = new UserAuthDetails();
        c.username = user.getUsername();
        c.password = user.getPassword();
        c.role = user.getRole();
        c.admin = user.getRole().isAdmin();
        c.id = user.getId();

        return c;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role.getPermissions();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public boolean isAdmin() {
        return admin;
    }
}
