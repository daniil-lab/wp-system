package com.wp.system.dto.user;

import com.wp.system.entity.user.UserRole;

import java.util.UUID;

public class UserRoleDTO {
    private UUID id;

    private String name;

    private boolean autoApply;

    public UserRoleDTO() {};

    public UserRoleDTO(UserRole role) {
        this.id = role.getId();
        this.name = role.getName();
        this.autoApply = role.isAutoApply();
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
