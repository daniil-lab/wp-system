package com.wp.system.dto.user;

import com.wp.system.entity.user.User;
import com.wp.system.entity.user.UserEmail;
import com.wp.system.utils.WalletType;
import com.wp.system.utils.user.UserType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

public class UserDTO {
    @Schema(description = "ID пользователя")
    private UUID id;

    @Schema(description = "Номер телефона пользователя")
    private String username;

    @Schema(description = "Роль пользователя")
    private UserRoleDTO role;

    @Schema(description = "Электронная почта пользователя")
    private UserEmail email;

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

    private int plannedIncome;

    private boolean googleLink;

    private boolean notificationsEnable;

    private Instant createAt;

    private Integer plannedSpend;

    private Integer plannedEarn;

    public UserDTO() {};

    public UserDTO(User user) {
        if(user == null)
            return;

        this.username = user.getUsername();
        this.role = new UserRoleDTO(user.getRole());
        this.id = user.getId();
        this.email = user.getEmail();
        this.type = user.getUserType();
        this.walletType = user.getWallet();
        this.touchID = user.isTouchId();
        this.faceID = user.isFaceId();
        this.pinCode = user.getPinCode();
        this.notificationsEnable = user.isNotificationsEnable();
        this.plannedIncome = user.getPlannedIncome();
        this.createAt = user.getCreateAt();
        this.googleLink = user.getGoogleLink();
        this.plannedEarn = user.getPlannedEarn();
        this.plannedSpend = user.getPlannedSpend();
    }

    public Integer getPlannedSpend() {
        return plannedSpend;
    }

    public void setPlannedSpend(Integer plannedSpend) {
        this.plannedSpend = plannedSpend;
    }

    public Integer getPlannedEarn() {
        return plannedEarn;
    }

    public void setPlannedEarn(Integer plannedEarn) {
        this.plannedEarn = plannedEarn;
    }

    public boolean isGoogleLink() {
        return googleLink;
    }

    public void setGoogleLink(boolean googleLink) {
        this.googleLink = googleLink;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public int getPlannedIncome() {
        return this.plannedIncome;
    }

    public void setPlannedIncome(int plannedIncome) {
        this.plannedIncome = plannedIncome;
    }

    public boolean isNotificationsEnable() {
        return notificationsEnable;
    }

    public void setNotificationsEnable(boolean notificationsEnable) {
        this.notificationsEnable = notificationsEnable;
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

    public UserEmail getEmail() {
        return email;
    }

    public void setEmail(UserEmail email) {
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
