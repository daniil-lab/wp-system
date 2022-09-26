package com.wp.system.permissions.category;

import com.wp.system.permissions.Permission;

public enum BaseCategoryPermissions implements Permission {
    CREATE_BASE_CATEGORY("BASE_CATEGORY_PERMISSION_1", "Создание базовых категории", "Позволяет создавать базовые категории", "BASE_CATEGORY_CREATE"),
    UPDATE_BASE_CATEGORY("BASE_CATEGORY_PERMISSION_2","Изменение базовой категории", "Позволяет изменять базовые категории", "BASE_CATEGORY_UPDATE"),
    REMOVE_BASE_CATEGORY("BASE_CATEGORY_PERMISSION_3","Удаление базовой категории", "Позволяет удалять базовые категории", "BASE_CATEGORY_DELETE"),
    GET_BASE_CATEGORY("BASE_CATEGORY_PERMISSION_4","Получение базовых категорий", "Позволяет получать базовые категории", "BASE_CATEGORY_GET"),
    FULL_BASE_CATEGORY("BASE_CATEGORY_PERMISSION_5","Полный доступ для базовых категорий", "Позволяет выполнять любые операции над базовыми категориями", "BASE_CATEGORY_FULL");

    private String permissionId;

    private String permissionName;

    private String permissionDescription;

    private String permissionSystemValue;

    BaseCategoryPermissions(String permissionId, String permissionName, String permissionDescription, String permissionSystemValue) {
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
