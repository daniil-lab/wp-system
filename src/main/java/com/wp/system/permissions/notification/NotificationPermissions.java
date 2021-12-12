package com.wp.system.permissions.notification;

import com.wp.system.permissions.Permission;

public enum NotificationPermissions implements Permission {
    SEND_NOTIFICATION("NOTIFICATION_PERMISSION_1","Отправка уведомлений", "Позволяет отпралять уведомления", "NOTIFICATION_CREATE"),
    FULL_NOTIFICATION("NOTIFICATION_PERMISSION_2","Полный доступ к управлению уведомлениями", "Позволяет любые действия с уведомлениями", "NOTIFICATION_FULL");

    private String permissionId;

    private String permissionName;

    private String permissionDescription;

    private String permissionSystemValue;

    NotificationPermissions(String permissionId, String permissionName, String permissionDescription, String permissionSystemValue) {
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
