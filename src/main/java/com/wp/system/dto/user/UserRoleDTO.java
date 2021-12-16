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
