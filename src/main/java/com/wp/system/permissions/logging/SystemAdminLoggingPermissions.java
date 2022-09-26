package com.wp.system.permissions.logging;

import com.wp.system.permissions.Permission;

public enum SystemAdminLoggingPermissions implements Permission {
    CREATE_ADMIN_LOG("ADMIN_LOGGING_PERMISSION_1", "Создание админ лога", "Позволяет создавать админ логи", "ADMIN_LOG_CREATE"),
    REMOVE_ADMIN_LOG("ADMIN_LOGGING_PERMISSION_2","Удаление админ лога", "Позволяет удалять админ логи", "ADMIN_LOG_DELETE"),
    GET_ADMIN_LOG("ADMIN_LOGGING_PERMISSION_3","Получение админ логов", "Позволяет получать админ логи", "ADMIN_LOG_GET"),
    FULL_ADMIN_LOG("ADMIN_LOGGING_PERMISSION_4","Полный доступ для админ логов", "Позволяет выполнять любые операции над админ логами", "ADMIN_LOG_FULL");

    private String permissionId;

    private String permissionName;

    private String permissionDescription;

    private String permissionSystemValue;

    SystemAdminLoggingPermissions(String permissionId, String permissionName, String permissionDescription, String permissionSystemValue) {
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