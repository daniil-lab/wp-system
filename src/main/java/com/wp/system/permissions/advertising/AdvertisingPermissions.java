package com.wp.system.permissions.advertising;

import com.wp.system.permissions.Permission;

public enum AdvertisingPermissions implements Permission {
    CREATE_ADVERTISING("ADVERTISING_PERMISSION_1", "Создание рекламы", "Позволяет создавать рекламу", "ADVERTISING_CREATE"),
    UPDATE_ADVERTISING("ADVERTISING_PERMISSION_2","Изменение рекламы", "Позволяет изменять рекламу", "ADVERTISING_UPDATE"),
    REMOVE_ADVERTISING("ADVERTISING_PERMISSION_3","Удаление рекламы", "Позволяет удалять рекламу", "ADVERTISING_DELETE"),
    GET_ADVERTISING("ADVERTISING_PERMISSION_4","Получение рекламы", "Позволяет получать рекламу", "ADVERTISING_GET"),
    FULL_ADVERTISING("ADVERTISING_PERMISSION_5","Полный доступ для рекламы", "Позволяет выполнять любые операции над рекламой", "ADVERTISINGL_FULL");

    private String permissionId;

    private String permissionName;

    private String permissionDescription;

    private String permissionSystemValue;

    AdvertisingPermissions(String permissionId, String permissionName, String permissionDescription, String permissionSystemValue) {
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
