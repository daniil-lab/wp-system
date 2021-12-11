package com.wp.system.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wp.system.permissions.Permission;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class UserRolePermission implements GrantedAuthority {
    @Id
    private UUID id = UUID.randomUUID();

    private String permission;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="role_id")
    @JsonIgnoreProperties({"permissions", "hibernateLazyInitializer", "handler"})
    private UserRole role;

    public UserRolePermission() {};

    public UserRolePermission(Permission permissionList, UserRole role) {
        this.permission = permissionList.getPermissionSystemValue();
        this.role = role;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String getAuthority() {
        return this.permission;
    }
}
