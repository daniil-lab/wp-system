package com.wp.system.permissions.tinkoff;

import com.wp.system.permissions.Permission;

public enum TinkoffPermissions implements Permission {
    CREATE_TINKOFF("TINKOFF_PERMISSION_1", "Создание интеграции Tinkoff", "Позволяет создавать интеграции", "TINKOFF_CREATE"),
    REMOVE_TINKOFF("TINKOFF_PERMISSION_3","Удаление интеграции Tinkoff", "Позволяет удалять интеграции", "TINKOFF_DELETE"),
    GET_TINKOFF("TINKOFF_PERMISSION_4","Получение интеграции Tinkoff", "Позволяет получать интеграции", "TINKOFF_GET"),
    SYNC_TINKFOFF("TINKOFF_PERMISSION_5","Синхронизация по интеграции Tinkoff", "Позволяет синхронизироваться по интеграции", "TINKOFF_SYNC"),
    FULL_TINKOFF("TINKOFF_PERMISSION_6","Полный доступ для интеграций Tinkoff", "Позволяет выполнять любые операции над интеграциями", "TINKOFF_FULL"),
    UPDATE_TINKOFF("TINKOFF_PERMISSION_7","Обновление Tinkoff данных", "Позволяет выполнять операции обновления транзакций и прочего", "TINKOFF_UPDATE");

    private String permissionId;

    private String permissionName;

    private String permissionDescription;

    private String permissionSystemValue;

    TinkoffPermissions(String permissionId, String permissionName, String permissionDescription, String permissionSystemValue) {
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
