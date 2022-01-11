package com.wp.system.dto.user;

import com.wp.system.entity.user.User;
import com.wp.system.entity.user.UserSubscription;
import com.wp.system.other.WalletType;
import com.wp.system.other.user.UserType;
import io.swagger.v3.oas.annotations.media.Schema;

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
    private boolean touchID;

    @Schema(description = "Включена ли проверка по FaceID пользователя")
    private boolean faceID;

    @Schema(description = "Пин-код пользователя")
    private String pinCode;

    private UserSubscription subscription;

    public UserDTO() {};

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.role = new UserRoleDTO(user.getRole());
        this.id = user.getId();
        this.email = user.getEmail();
        this.type = user.getUserType();
        this.walletType = user.getWallet();
        this.touchID = user.isTouchId();
        this.faceID = user.isFaceId();
        this.pinCode = user.getPinCode();
        this.subscription = user.getSubscription();
    }

    public UserSubscription getSubscription() {
        return subscription;
    }

    public void setSubscription(UserSubscription subscription) {
        this.subscription = subscription;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public boolean isTouchID() {
        return touchID;
    }

    public void setTouchID(boolean touchID) {
        this.touchID = touchID;
    }

    public boolean isFaceID() {
        return faceID;
    }

    public void setFaceID(boolean faceID) {
        this.faceID = faceID;
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
