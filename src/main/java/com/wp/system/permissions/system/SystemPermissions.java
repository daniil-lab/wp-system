package com.wp.system.permissions.system;

import com.wp.system.permissions.Permission;

public enum SystemPermissions implements Permission {
    UPDATE_PUBLIC_DATA("PUBLIC_DATA_PERMISSION_1", "Обновление публичных данных", "Позволяет обновлять публичные даннные", "PUBLIC_DATA_UPDATE");

    private String permissionId;

    private String permissionName;

    private String permissionDescription;

    private String permissionSystemValue;

    SystemPermissions(String permissionId, String permissionName, String permissionDescription, String permissionSystemValue) {
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
