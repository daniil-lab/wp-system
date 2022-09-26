package com.wp.system.request.user;

import com.wp.system.utils.ValidationErrorMessages;
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

    @Schema(required = true, description = "Является ли данная роль ролью для заблокированных.")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private Optional<Boolean> roleForBlocked;

    @Schema(required = true)
    private Optional<Boolean> roleAfterBuy;

    @Schema(required = true)
    private Optional<Boolean> roleAfterBuyExpiration;

    public UpdateUserRoleRequest() {};

    public UpdateUserRoleRequest(String name, Optional<Boolean> autoApply) {
        this.name = name;
        this.autoApply = autoApply;
    }

    public Optional<Boolean> getRoleForBlocked() {
        return roleForBlocked;
    }

    public void setRoleForBlocked(Optional<Boolean> roleForBlocked) {
        this.roleForBlocked = roleForBlocked;
    }

    public Optional<Boolean> getRoleAfterBuy() {
        return roleAfterBuy;
    }

    public void setRoleAfterBuy(Optional<Boolean> roleAfterBuy) {
        this.roleAfterBuy = roleAfterBuy;
    }

    public Optional<Boolean> getRoleAfterBuyExpiration() {
        return roleAfterBuyExpiration;
    }

    public void setRoleAfterBuyExpiration(Optional<Boolean> roleAfterBuyExpiration) {
        this.roleAfterBuyExpiration = roleAfterBuyExpiration;
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
