package com.wp.system.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.wp.system.other.ValidationErrorMessages;
import com.wp.system.other.WalletType;
import com.wp.system.other.user.UserType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Optional;

public class CreateUserRequest {
    @Schema(required = true, description = "Номер телефона")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    @Pattern(regexp = "^((\\+7)+([0-9]){10})$", message = ValidationErrorMessages.PHONE_VALIDATION_FAILED)
    private String username;

    @Schema(required = true, description = "Пароль, закодированный в Base64")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
//    @Size(min = 6, max = 32, message = ValidationErrorMessages.INVALID_PASSWORD_LENGTH)
    @Pattern(regexp = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$")
    @Length(min = 1)
    private String password;

    @Schema(required = true, description = "Валюта пользователя")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private WalletType walletType;

    @Schema(required = true, description = "Электронная почта")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = ValidationErrorMessages.EMAIL_VALIDATION_FAILED)
    private String email;

    @Schema(required = true, description = "Тип пользователя. По стандарту передавать SYSTEM," +
            "если пользователь авторизовывался через APPLE или др., то указать соответствующее значение из списка.")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private UserType type;

    @Schema(required = false, description = "Название роли. Если таковой существовать не будет, выдаст ошибку." +
            "Если опустить, то попытается найти роль с полем 'autoApply': true и прикрепить ее к пользователю." +
            "Если таковой не будет, и поле будет отсутствовать, то выдаст ошибку.")
    private String roleName;

    public CreateUserRequest() {};

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public WalletType getWalletType() {
        return walletType;
    }

    public void setWalletType(WalletType walletType) {
        this.walletType = walletType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
