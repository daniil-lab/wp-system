package com.wp.system.permissions.logging;

import com.wp.system.permissions.Permission;

public enum SystemErrorLoggingPermissions implements Permission {
    CREATE_ERROR_LOG("ERROR_LOGGING_PERMISSION_1", "Создание лога ошибки", "Позволяет создавать логи ошибок", "ERROR_LOG_CREATE"),
    REMOVE_ERROR_LOG("ERROR_LOGGING_PERMISSION_2","Удаление лога ошибки", "Позволяет удалять логи ошибок", "ERROR_LOG_DELETE"),
    GET_ERROR_LOG("ERROR_LOGGING_PERMISSION_3","Получение логов ошибок", "Позволяет получать логи ошибок", "ERROR_LOG_GET"),
    FULL_ERROR_LOG("ERROR_LOGGING_PERMISSION_4","Полный доступ для логов ошибок", "Позволяет выполнять любые операции над логами ошибок", "ERROR_LOG_FULL");

    private String permissionId;

    private String permissionName;

    private String permissionDescription;

    private String permissionSystemValue;

    SystemErrorLoggingPermissions(String permissionId, String permissionName, String permissionDescription, String permissionSystemValue) {
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