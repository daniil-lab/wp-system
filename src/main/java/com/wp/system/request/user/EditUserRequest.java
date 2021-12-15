package com.wp.system.request.user;

import com.wp.system.other.ValidationErrorMessages;
import com.wp.system.other.WalletType;
import com.wp.system.other.user.UserType;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

public class EditUserRequest {
    @Pattern(regexp = "^((\\+7|7|8)+([0-9]){10})$", message = ValidationErrorMessages.PHONE_VALIDATION_FAILED)
    private String username;

    @Size(min = 6, max = 32, message = ValidationErrorMessages.INVALID_PASSWORD_LENGTH)
    private String password;

    private WalletType walletType;

    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = ValidationErrorMessages.EMAIL_VALIDATION_FAILED)
    private String email;

    private UserType type;

    private String roleName;

    private Boolean notificationsEnable;

    @PositiveOrZero(message = ValidationErrorMessages.PLANNED_INCOME_NEGATIVE)
    private Integer plannedIncome;

    public EditUserRequest() {}

    public EditUserRequest(String username, String password, WalletType walletType, String email, UserType type, String roleName) {
        this.username = username;
        this.password = password;
        this.walletType = walletType;
        this.email = email;
        this.type = type;
        this.roleName = roleName;
    }

    public Integer getPlannedIncome() {
        return plannedIncome;
    }

    public void setPlannedIncome(Integer plannedIncome) {
        this.plannedIncome = plannedIncome;
    }

    public Boolean getNotificationsEnable() {
        return notificationsEnable;
    }

    public void setNotificationsEnable(Boolean notificationsEnable) {
        this.notificationsEnable = notificationsEnable;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
