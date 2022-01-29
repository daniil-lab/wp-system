package com.wp.system.permissions.subscription;

import com.wp.system.permissions.Permission;

public enum SubscriptionPermissions implements Permission {
    GET_SUBSCRIPTION("SUBSCRIPTION_PERMISSION_1","Получение подписок пользователя", "Позволяет получать подписки пользователя", "SUBSCRIPTION_GET"),
    UPDATE_SUBSCRIPTION("SUBSCRIPTION_PERMISSION_2","Изменение подписок пользователя", "Позволяет изменять подписки пользователя", "SUBSCRIPTION_UPDATE"),
    FULL_SUBSCRIPTION("SUBSCRIPTION_PERMISSION_3","Полный доступ для подписок", "Позволяет выполнять любые операции над подписками", "SUBSCRIPTION_FULL");

    private String permissionId;

    private String permissionName;

    private String permissionDescription;

    private String permissionSystemValue;

    SubscriptionPermissions(String permissionId, String permissionName, String permissionDescription, String permissionSystemValue) {
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
