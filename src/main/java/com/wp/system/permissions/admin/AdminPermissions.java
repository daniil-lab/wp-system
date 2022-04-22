package com.wp.system.permissions.admin;

import com.wp.system.permissions.Permission;

public enum AdminPermissions implements Permission {
    ADMIN_REMOVE_USER("ADMIN_PERMISSION_1", "АДМИН-ДОСТУП: Удаление пользователя", "Позволяет удалять пользователей", "ADMIN_REMOVE_USER"),
    ADMIN_UPDATE_USER("ADMIN_PERMISSION_3","АДМИН-ДОСТУП: Обновление пользователя", "Позволяет обновлять данные пользователей", "ADMIN_UPDATE_USER"),
    ADMIN_BILL_TRANSACTIONS("ADMIN_PERMISSION_4","АДМИН-ДОСТУП: Получение транзакций счетов", "Позволяет получать транзакции по счетам", "ADMIN_BILL_TRANSACTIONS"),
    ADMIN_GET_USER("ADMIN_PERMISSION_5","АДМИН-ДОСТУП: Получение пользователей", "Позволяет получать пользователей", "ADMIN_GET_USER"),
    ADMIN_SBER_TRANSACTIONS("ADMIN_PERMISSION_6","АДМИН-ДОСТУП: Получение транзакций Сбер", "Позволяет получать транзакции Сбера", "ADMIN_SBER_TRANSACTIONS"),
    ADMIN_TOCHKA_TRANSACTIONS("ADMIN_PERMISSION_7","АДМИН-ДОСТУП: Получение транзакций Точки", "Позволяет получать транзакции Точки", "ADMIN_TOCHKA_TRANSACTIONS"),
    ADMIN_TINKOFF_TRANSACTIONS("ADMIN_PERMISSION_8","АДМИН-ДОСТУП: Получение транзакций Тинькофф", "Позволяет получать транзакции Тинькофф", "ADMIN_TINKOFF_TRANSACTIONS"),
    ADMIN_USER_BILLS("ADMIN_PERMISSION_9","АДМИН-ДОСТУП: Получение счетов", "Позволяет получать счета", "ADMIN_USER_BILLS"),
    ADMIN_USER_CATEGORIES("ADMIN_PERMISSION_10","АДМИН-ДОСТУП: Получение категорий", "Позволяет получать категории", "ADMIN_USER_CATEGORIES"),
    ADMIN_TOCHKA_CARDS("ADMIN_PERMISSION_11","АДМИН-ДОСТУП: Обновление карт Точки", "Позволяет получать карты Точки", "ADMIN_TOCHKA_CARDS"),
    ADMIN_SBER_CARDS("ADMIN_PERMISSION_12","АДМИН-ДОСТУП: Получение карт Сбер", "Позволяет получать карты Сбера", "ADMIN_SBER_CARDS"),
    ADMIN_TINKOFF_CARDS("ADMIN_PERMISSION_13","АДМИН-ДОСТУП: Получение карт Тинькофф", "Позволяет получать карты Тинькофф", "ADMIN_TINKOFF_CARDS"),
    ADMIN_USER_ACTIVITY_GET("ADMIN_PERMISSION_14","АДМИН-ДОСТУП: Получение активности", "Позволяет получать активности", "ADMIN_USER_ACTIVITY_GET"),
    ADMIN_USER_SUBSCRIPTION_GET("ADMIN_PERMISSION_15","АДМИН-ДОСТУП: Получение подписок", "Позволяет получать подписки", "ADMIN_USER_SUBSCRIPTION_GET"),
    ADMIN_USER_SUBSCRIPTION_RESET("ADMIN_PERMISSION_16","АДМИН-ДОСТУП: Сброс подписок", "Позволяет аннулировать подписки", "ADMIN_USER_SUBSCRIPTION_RESET"),
    ADMIN_USER_SUBSCRIPTION_EXTEND("ADMIN_PERMISSION_17","АДМИН-ДОСТУП: Продление подписок", "Позволяет продливать подписки", "ADMIN_USER_SUBSCRIPTION_EXTEND"),
    ADMIN_USER_RESET_PIN("ADMIN_PERMISSION_18","АДМИН-ДОСТУП: Сброс пин кода", "Позволяет выполнять сброс пинкода", "ADMIN_USER_RESET_PIN"),
    ADMIN_USER_LOYALTY_CARDS("ADMIN_PERMISSION_19","АДМИН-ДОСТУП: Получение карт лояльностей", "Позволяет получать карты лояльностей", "ADMIN_USER_LOYALTY_CARDS"),
    ADMIN_FULL("ADMIN_PERMISSION_20","АДМИН-ДОСТУП: Полный доступ", "Дает полный доступ для админ действий", "ADMIN_FULL");

    private String permissionId;

    private String permissionName;

    private String permissionDescription;

    private String permissionSystemValue;

    AdminPermissions(String permissionId, String permissionName, String permissionDescription, String permissionSystemValue) {
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
