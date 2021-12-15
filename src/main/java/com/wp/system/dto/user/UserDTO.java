package com.wp.system.dto.user;

import com.wp.system.entity.user.User;
import com.wp.system.entity.user.UserRole;
import com.wp.system.other.WalletType;
import com.wp.system.other.user.UserType;

import java.util.List;
import java.util.UUID;

public class UserDTO {
    private UUID id;

    private String username;

    private UserRoleDTO role;

    private String email;

    private UserType type;

    private WalletType walletType;

    public UserDTO() {};

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.role = new UserRoleDTO(user.getRole());
        this.id = user.getId();
        this.email = user.getEmail();
        this.type = user.getUserType();
        this.walletType = user.getWallet();
    }

    public WalletType getWalletType() {
        return walletType;
    }

    public void setWalletType(WalletType walletType) {
        this.walletType = walletType;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRoleDTO getRole() {
        return role;
    }

    public void setRole(UserRoleDTO role) {
        this.role = role;
    }
}
