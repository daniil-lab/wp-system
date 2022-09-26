package com.wp.system.request.user;

import com.wp.system.utils.ValidationErrorMessages;
import com.wp.system.utils.WalletType;
import com.wp.system.utils.user.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

public class EditUserRequest {
    @Schema(required = false, description = "Номер телефона")
    @Pattern(regexp = "^((\\+7)+([0-9]){10})$", message = ValidationErrorMessages.PHONE_VALIDATION_FAILED)
    private String username;

    @Schema(required = false, description = "Пароль, закодированный в Base64")
//    @Size(min = 6, max = 32, message = ValidationErrorMessages.INVALID_PASSWORD_LENGTH)
    @Pattern(regexp = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$")
    @Length(min = 1)
    private String password;

    @Schema(required = false, description = "Валюта пользователя")
    private WalletType walletType;

    @Schema(required = false, description = "Электронная почта")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = ValidationErrorMessages.EMAIL_VALIDATION_FAILED)
    private String email;

    @Schema(required = true, description = "Тип пользователя. По стандарту передавать SYSTEM," +
            "если пользователь авторизовывался через APPLE или др., то указать соответствующее значение из списка.")
    private UserType type;

    @Schema(required = false, description = "Название роли. Если таковой существовать не будет, выдаст ошибку." +
            "Если опустить, то попытается найти роль с полем 'autoApply': true и прикрепить ее к пользователю." +
            "Если таковой не будет, и поле будет отсутствовать, то выдаст ошибку.")
    private String roleName;

    @Schema(required = false, description = "Включенные уведомления. true - включены, false - отключены")
    private Boolean notificationsEnable;

    @Schema(required = false, description = "Запланированный доход")
    @PositiveOrZero(message = ValidationErrorMessages.PLANNED_INCOME_NEGATIVE)
    private Integer plannedIncome;

    @Schema(required = false, description = "Проверка по FaceID")
    private Boolean faceId;

    @Schema(required = false, description = "Проверка по TouchID")
    private Boolean touchId;

    public EditUserRequest() {}

    public EditUserRequest(String username, String password, WalletType walletType, String email, UserType type, String roleName) {
        this.username = username;
        this.password = password;
        this.walletType = walletType;
        this.email = email;
        this.type = type;
        this.roleName = roleName;
    }

    public Boolean getFaceId() {
        return faceId;
    }

    public void setFaceId(Boolean faceId) {
        this.faceId = faceId;
    }

    public Boolean getTouchId() {
        return touchId;
    }

    public void setTouchId(Boolean touchId) {
        this.touchId = touchId;
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
