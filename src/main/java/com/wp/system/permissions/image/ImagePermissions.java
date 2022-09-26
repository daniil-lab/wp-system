package com.wp.system.permissions.image;

import com.wp.system.permissions.Permission;

public enum ImagePermissions implements Permission {
    REMOVE_IMAGES("IMAGES_PERMISSION_1","Удаление изображений", "Позволяет удалять изображения", "IMAGES_DELETE"),
    GET_IMAGES("IMAGES_PERMISSION_2","Получение изображений", "Позволяет получать изображения", "IMAGES_GET"),
    CREATE_IMAGES("IMAGES_PERMISSION_3","Создание изображений", "Позволяет создавать изображения", "IMAGES_CREATE"),
    FULL_IMAGES("IMAGES_PERMISSION_4","Полный доступ для изображений", "Позволяет выполнять любые операции над изображениями", "IMAGES_FULL");

    private String permissionId;

    private String permissionName;

    private String permissionDescription;

    private String permissionSystemValue;

    ImagePermissions(String permissionId, String permissionName, String permissionDescription, String permissionSystemValue) {
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
