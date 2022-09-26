package com.wp.system.permissions.user;

import com.wp.system.permissions.Permission;

public enum UserRolePermissionPermissions implements Permission {
    CREATE_USER_ROLE_PERMISSION("USER_ROLE_PERMISSION_PERMISSION_1", "Создание доступа роли пользователя", "Позволяет прикреплять доступы к роли пользователей", "USER_ROLE_PERMISSION_CREATE"),
    REMOVE_USER_ROLE_PERMISSION("USER_ROLE_PERMISSION_PERMISSION_3","Удаление доступа роли пользователя", "Позволяет удалять доступы роли пользователей", "USER_ROLE_PERMISSION_DELETE"),
    GET_USER_ROLE_PERMISSION("USER_ROLE_PERMISSION_PERMISSION_4","Получение доступа роли пользователя", "Позволяет получать доступы роли пользователей", "USER_ROLE_PERMISSION_GET"),
    FULL_USER_ROLE_PERMISSION("USER_ROLE_PERMISSION_PERMISSION_5","Полный доступ для доступов роли пользователя", "Позволяет любые действия с доступами ролями пользователей", "USER_ROLE_PERMISSION_FULL");

    private String permissionId;

    private String permissionName;

    private String permissionDescription;

    private String permissionSystemValue;

    UserRolePermissionPermissions(String permissionId, String permissionName, String permissionDescription, String permissionSystemValue) {
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
