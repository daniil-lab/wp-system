package com.wp.system.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class UserRole {
    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<UserRolePermission> permissions = new ArrayList<>();

    private boolean autoApply;

    private boolean isAdmin;

    private boolean roleAfterBuy;

    private boolean roleAfterBuyExpiration;

    private boolean roleForBlocked;

    public UserRole() {};

    public UserRole(String name, boolean autoApply) {
        this.name = name;
        this.autoApply = autoApply;
    }

    public boolean isRoleForBlocked() {
        return roleForBlocked;
    }

    public void setRoleForBlocked(boolean roleForBlocked) {
        this.roleForBlocked = roleForBlocked;
    }

    public boolean isRoleAfterBuy() {
        return roleAfterBuy;
    }

    public void setRoleAfterBuy(boolean roleAfterBuy) {
        this.roleAfterBuy = roleAfterBuy;
    }

    public boolean isRoleAfterBuyExpiration() {
        return roleAfterBuyExpiration;
    }

    public void setRoleAfterBuyExpiration(boolean roleAfterBuyExpiration) {
        this.roleAfterBuyExpiration = roleAfterBuyExpiration;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isAutoApply() {
        return autoApply;
    }

    public void setAutoApply(boolean autoApply) {
        this.autoApply = autoApply;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserRolePermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<UserRolePermission> permissions) {
        this.permissions = permissions;
    }
}
