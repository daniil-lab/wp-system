package com.wp.system.permissions.activity;

import com.wp.system.permissions.Permission;

public enum ActivityPermissions implements Permission {
    CREATE_ACTIVITY("ACTIVITY_PERMISSION_1", "Создание активности", "Позволяет создавать активности", "ACTIVITY_CREATE"),
    REMOVE_ACTIVITY("ACTIVITY_PERMISSION_2","Удаление активности", "Позволяет удалять активности", "ACTIVITY_DELETE"),
    GET_ACTIVITY("ACTIVITY_PERMISSION_3","Получение активности", "Позволяет получать активности", "ACTIVITY_GET"),
    FULL_ACTIVITY("ACTIVITY_PERMISSION_4","Полный доступ для активности", "Позволяет выполнять любые операции над активностями", "ACTIVITY_FULL");

    private String permissionId;

    private String permissionName;

    private String permissionDescription;

    private String permissionSystemValue;

    ActivityPermissions(String permissionId, String permissionName, String permissionDescription, String permissionSystemValue) {
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
