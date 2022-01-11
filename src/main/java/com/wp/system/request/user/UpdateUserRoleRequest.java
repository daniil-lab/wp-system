package com.wp.system.request.user;

import com.wp.system.other.ValidationErrorMessages;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public class UpdateUserRoleRequest {
    @Schema(required = true, description = "Название роли")
    private String name;

    @Schema(required = true, description = "Автоматическое применение к пользователю после создания." +
            " Может существовать исключительно одна таковая. Если такая уже существует, выдаст ошибку.")
    private Optional<Boolean> autoApply;

    @Schema(required = true, description = "Является ли данная роль ролью администратора.")
    private Optional<Boolean> isAdmin;

    public UpdateUserRoleRequest() {};

    public UpdateUserRoleRequest(String name, Optional<Boolean> autoApply) {
        this.name = name;
        this.autoApply = autoApply;
    }

    public Optional<Boolean> getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Optional<Boolean> isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return name;
    }

    public Optional<Boolean> getAutoApply() {
        return autoApply;
    }

    public void setAutoApply(Optional<Boolean> autoApply) {
        this.autoApply = autoApply;
    }

    public void setName(String name) {
        this.name = name;
    }
}
