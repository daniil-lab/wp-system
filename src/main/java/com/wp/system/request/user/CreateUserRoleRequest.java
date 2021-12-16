package com.wp.system.request.user;

import com.wp.system.other.ValidationErrorMessages;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

public class CreateUserRoleRequest {
    @Schema(required = true, description = "Название роли")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private String name;

    @Schema(required = true, description = "Автоматическое применение к пользователю после создания." +
            " Может существовать исключительно одна таковая. Если такая уже существует, выдаст ошибку.")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private Optional<Boolean> autoApply;

    public CreateUserRoleRequest() {};

    public CreateUserRoleRequest(String name, Optional<Boolean> autoApply) {
        this.name = name;
        this.autoApply = autoApply;
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
