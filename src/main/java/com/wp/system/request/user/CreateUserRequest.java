package com.wp.system.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.wp.system.other.ValidationErrorMessages;
import com.wp.system.other.WalletType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Optional;

public class CreateUserRequest {
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    @Pattern(regexp = "^((\\+7|7|8)+([0-9]){10})$", message = ValidationErrorMessages.PHONE_VALIDATION_FAILED)
    private String username;

    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    @Size(min = 6, max = 32, message = ValidationErrorMessages.INVALID_PASSWORD_LENGTH)
    private String password;

    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private WalletType walletType;

    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = ValidationErrorMessages.EMAIL_VALIDATION_FAILED)
    private String email;

    private String roleName;

    public CreateUserRequest() {};

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
