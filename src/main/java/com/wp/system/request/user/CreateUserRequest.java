package com.wp.system.request.user;

import com.wp.system.other.ValidationErrorMessages;
import com.wp.system.other.WalletType;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public class CreateUserRequest {
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private String username;

    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private String password;

    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private WalletType walletType;

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
}
