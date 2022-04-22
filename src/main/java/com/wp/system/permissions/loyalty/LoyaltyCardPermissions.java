package com.wp.system.permissions.loyalty;

import com.wp.system.permissions.Permission;

public enum LoyaltyCardPermissions implements Permission {
    CREATE_LOYALTY_CARD("LOYALTY_CARD_PERMISSION_1", "Создание карты лояльности", "Позволяет создавать карты лояльности", "LOYALTY_CARD_CREATE"),
    UPDATE_LOYALTY_CARD("LOYALTY_CARD_PERMISSION_2","Изменение карты лояльности", "Позволяет изменять карты лояльности", "LOYALTY_CARD_UPDATE"),
    REMOVE_LOYALTY_CARD("LOYALTY_CARD_PERMISSION_3","Удаление карты лояльности", "Позволяет удалять карты лояльности", "LOYALTY_CARD_DELETE"),
    GET_LOYALTY_CARD("LOYALTY_CARD_PERMISSION_4","Получение карт лояльности", "Позволяет получать карты лояльности", "LOYALTY_CARD_GET"),
    LOYALTY_CARD_GET_CUSTOM_IMAGE("LOYALTY_CARD_PERMISSION_5","Получение кастомной картинки карты лояльности", "Позволяет получать кастомные изображения карты лояльностей", "LOYALTY_CARD_GET_CUSTOM_IMAGE"),
    LOYALTY_CARD_UPLOAD_CUSTOM_IMAGE("LOYALTY_CARD_PERMISSION_6","Загрузка кастомной картинки карты лояльности", "Позволяет загружать кастомные картинку карты лояльности", "LOYALTY_CARD_UPLOAD_CUSTOM_IMAGE"),
    LOYALTY_CARD_REMOVE_CUSTOM_IMAGE("LOYALTY_CARD_PERMISSION_7","Удаление кастомной картинки карты лояльности", "Позволяет удалять кастомные картинки карты лояльности", "LOYALTY_CARD_REMOVE_CUSTOM_IMAGE"),
    FULL_LOYALTY_CARD("LOYALTY_CARD_PERMISSION_5","Полный доступ для карт лояльности", "Позволяет выполнять любые операции над картами лояльности", "LOYALTY_CARD_FULL");

    private String permissionId;

    private String permissionName;

    private String permissionDescription;

    private String permissionSystemValue;

    LoyaltyCardPermissions(String permissionId, String permissionName, String permissionDescription, String permissionSystemValue) {
        this.permissionId = permissionId;
        this.permissionName = permissionName;
        this.permissionDescription = permissionDescription;
        this.permissionSystemValue = permissionSystemValue;
    }

    @Override
    public String getPermissionId() {
        return this.permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public String getPermissionDescription() {
        return permissionDescription;
    }

    public String getPermissionSystemValue() {
        return permissionSystemValue;
    }
}
