package com.wp.system.dto.user;

import com.wp.system.entity.user.User;
import com.wp.system.entity.user.UserRole;
import com.wp.system.other.WalletType;
import com.wp.system.other.user.UserType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

public class UserDTO {
    @Schema(description = "ID пользователя")
    private UUID id;

    @Schema(description = "Номер телефона пользователя")
    private String username;

    @Schema(description = "Роль пользователя")
    private UserRoleDTO role;

    @Schema(description = "Электронная почта пользователя")
    private String email;

    @Schema(description = "Тип пользователя")
    private UserType type;

    @Schema(description = "Валюта пользователя")
    private WalletType walletType;

    @Schema(description = "Включена ли проверка по TouchID пользователя")
    private boolean touchId;

    @Schema(description = "Включена ли проверка по FaceID пользователя")
    private boolean faceId;

    public UserDTO() {};

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.role = new UserRoleDTO(user.getRole());
        this.id = user.getId();
        this.email = user.getEmail();
        this.type = user.getUserType();
        this.walletType = user.getWallet();
        this.touchId = user.isTouchId();
        this.faceId = user.isFaceId();
    }

    public boolean isTouchId() {
        return touchId;
    }

    public void setTouchId(boolean touchId) {
        this.touchId = touchId;
    }

    public boolean isFaceId() {
        return faceId;
    }

    public void setFaceId(boolean faceId) {
        this.faceId = faceId;
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
