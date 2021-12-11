package com.wp.system.permissions.user;

import com.wp.system.permissions.Permission;

public enum UserRolePermissions implements Permission {
    CREATE_USER_ROLE("USER_ROLE_PERMISSION_1", "Создание роли пользователя", "Позволяет создавать роли пользователей", "USER_ROLE_CREATE"),
    UPDATE_USER_ROLE("USER_ROLE_PERMISSION_2","Изменение роли пользователя", "Позволяет изменять роли пользователей", "USER_ROLE_UPDATE"),
    REMOVE_USER_ROLE("USER_ROLE_PERMISSION_3","Удаление роли пользователя", "Позволяет удалять роли пользователей", "USER_ROLE_DELETE"),
    GET_USER_ROLE("USER_ROLE_PERMISSION_4","Получение роли пользователя", "Позволяет получать роли пользователей", "USER_ROLE_GET"),
    FULL_USER_ROLE("USER_ROLE_PERMISSION_5","Полный доступ для роли пользователя", "Позволяет любые действия с ролями пользователей", "USER_ROLE_FULL");

    private String permissionId;

    private String permissionName;

    private String permissionDescription;

    private String permissionSystemValue;

    UserRolePermissions(String permissionId, String permissionName, String permissionDescription, String permissionSystemValue) {
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
