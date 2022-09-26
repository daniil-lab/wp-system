package com.wp.system.permissions.category;

import com.wp.system.permissions.Permission;

public enum CategoryPermissions implements Permission {
    CREATE_CATEGORY("CATEGORY_PERMISSION_1", "Создание категории", "Позволяет создавать категории", "CATEGORY_CREATE"),
    UPDATE_CATEGORY("CATEGORY_PERMISSION_2","Изменение категории", "Позволяет изменять категории", "CATEGORY_UPDATE"),
    REMOVE_CATEGORY("CATEGORY_PERMISSION_3","Удаление категории", "Позволяет удалять категории", "CATEGORY_DELETE"),
    GET_CATEGORY("CATEGORY_PERMISSION_4","Получение категорий", "Позволяет получать категории", "CATEGORY_GET"),
    FULL_CATEGORY("CATEGORY_PERMISSION_5","Полный доступ для категорий", "Позволяет выполнять любые операции над категориями", "CATEGORY_FULL");

    private String permissionId;

    private String permissionName;

    private String permissionDescription;

    private String permissionSystemValue;

    CategoryPermissions(String permissionId, String permissionName, String permissionDescription, String permissionSystemValue) {
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
