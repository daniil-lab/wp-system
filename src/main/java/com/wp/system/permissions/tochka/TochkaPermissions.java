package com.wp.system.permissions.tochka;

import com.wp.system.permissions.Permission;

public enum TochkaPermissions implements Permission {
    CREATE_TOCHKA("TOCHKA_PERMISSION_1", "Создание интеграции Tochka", "Позволяет создавать интеграции", "TOCHKA_CREATE"),
    REMOVE_TOCHKA("TOCHKA_PERMISSION_3","Удаление интеграции Tochka", "Позволяет удалять интеграции", "TOCHKA_DELETE"),
    GET_TOCHKA("TOCHKA_PERMISSION_4","Получение интеграции Tochka", "Позволяет получать интеграции", "TOCHKA_GET"),
    SYNC_TOCHKA("TOCHKA_PERMISSION_5","Синхронизация по интеграции Tochka", "Позволяет синхронизироваться по интеграции", "TOCHKA_SYNC"),
    FULL_TOCHKA("TOCHKA_PERMISSION_6","Полный доступ для интеграций Tochka", "Позволяет выполнять любые операции над интеграциями", "TOCHKA_FULL"),
    UPDATE_TOCHKA("TOCHKA_PERMISSION_7","Обновление Tochka данных", "Позволяет выполнять операции обновления транзакций и прочего", "TOCHKA_UPDATE");

    private String permissionId;

    private String permissionName;

    private String permissionDescription;

    private String permissionSystemValue;

    TochkaPermissions(String permissionId, String permissionName, String permissionDescription, String permissionSystemValue) {
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
