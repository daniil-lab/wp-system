package com.wp.system.permissions.sber;

import com.wp.system.permissions.Permission;

public enum SberPermissions implements Permission {
    CREATE_SBER("SBER_PERMISSION_1", "Создание интеграции Sber", "Позволяет создавать интеграции", "SBER_CREATE"),
    REMOVE_SBER("SBER_PERMISSION_3","Удаление интеграции Sber", "Позволяет удалять интеграции", "SBER_DELETE"),
    GET_SBER("SBER_PERMISSION_4","Получение интеграции Sber", "Позволяет получать интеграции", "SBER_GET"),
    SYNC_SBER("SBER_PERMISSION_5","Синхронизация по интеграции Sber", "Позволяет синхронизироваться по интеграции", "SBER_SYNC"),
    FULL_SBER("SBER_PERMISSION_6","Полный доступ для интеграций Sber", "Позволяет выполнять любые операции над интеграциями", "SBER_FULL"),
    UPDATE_SBER("SBER_PERMISSION_7","Обновление Sber данных", "Позволяет выполнять операции обновления транзакций и прочего", "SBER_UPDATE");

    private String permissionId;

    private String permissionName;

    private String permissionDescription;

    private String permissionSystemValue;

    SberPermissions(String permissionId, String permissionName, String permissionDescription, String permissionSystemValue) {
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
