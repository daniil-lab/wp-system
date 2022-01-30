package com.wp.system.dto.user;

import com.wp.system.entity.user.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public class UserRoleDTO {
    @Schema(description = "ID роли")
    private UUID id;

    @Schema(description = "Название роли")
    private String name;

    @Schema(description = "Автоприменение")
    private boolean autoApply;

    private boolean roleAfterBuy;

    private boolean roleAfterBuyExpiration;

    private boolean roleForBlocked;

    private boolean isAdmin;

    public UserRoleDTO() {};

    public UserRoleDTO(UserRole role) {
        this.id = role.getId();
        this.name = role.getName();
        this.autoApply = role.isAutoApply();
        this.roleAfterBuy = role.isRoleAfterBuy();
        this.roleAfterBuyExpiration = role.isRoleAfterBuyExpiration();
        this.isAdmin = role.isAdmin();
        this.roleForBlocked = role.isRoleForBlocked();
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAutoApply() {
        return autoApply;
    }

    public void setAutoApply(boolean autoApply) {
        this.autoApply = autoApply;
    }
}
