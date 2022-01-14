package com.wp.system.permissions.subscription;

import com.wp.system.permissions.Permission;

public enum SubscriptionVariantPermissions implements Permission {
    CREATE_SUBSCRIPTION_VARIANT("SUBSCRIPTION_VARIANT_PERMISSION_1", "Создание варианта подписки", "Позволяет создавать варианты подписок", "SUBSCRIPTION_VARIANT_CREATE"),
    UPDATE_SUBSCRIPTION_VARIANT("SUBSCRIPTION_VARIANT_PERMISSION_2","Изменение варианта подписки", "Позволяет изменять варианты подписок", "SUBSCRIPTION_VARIANT_UPDATE"),
    REMOVE_SUBSCRIPTION_VARIANT("SUBSCRIPTION_VARIANT_PERMISSION_3","Удаление варианта подписки", "Позволяет удалять варианты подписок", "SUBSCRIPTION_VARIANT_DELETE"),
    GET_SUBSCRIPTION_VARIANT("SUBSCRIPTION_VARIANT_PERMISSION_4","Получение вариантов подписки", "Позволяет получать варианты подписок", "SUBSCRIPTION_VARIANT_GET"),
    FULL_SUBSCRIPTION_VARIANT("SUBSCRIPTION_VARIANT_PERMISSION_5","Полный доступ для подписок", "Позволяет выполнять любые операции над подписками", "SUBSCRIPTION_VARIANT_FULL");

    private String permissionId;

    private String permissionName;

    private String permissionDescription;

    private String permissionSystemValue;

    SubscriptionVariantPermissions(String permissionId, String permissionName, String permissionDescription, String permissionSystemValue) {
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
