package com.wp.system.permissions.user;

import com.wp.system.permissions.Permission;

public enum UserPermissions implements Permission {
    CREATE_USER("USER_PERMISSION_1", "Создание пользователя", "Позволяет создавать пользователей", "USER_CREATE"),
    UPDATE_USER("USER_PERMISSION_2","Изменение пользователя", "Позволяет изменять пользователей", "USER_UPDATE"),
    REMOVE_USER("USER_PERMISSION_3","Удаление пользователя", "Позволяет удалять пользователей", "USER_DELETE"),
    GET_USER("USER_PERMISSION_4","Получение пользователя", "Позволяет получать пользователей", "USER_GET"),
    FULL_USER("USER_PERMISSION_5","Полный доступ для пользователя", "Позволяет выполнять любые операции над пользователем", "USER_FULL");

    private String permissionId;

    private String permissionName;

    private String permissionDescription;

    private String permissionSystemValue;

    UserPermissions(String permissionId, String permissionName, String permissionDescription, String permissionSystemValue) {
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
