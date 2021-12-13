package com.wp.system.permissions.bill;

import com.wp.system.permissions.Permission;

public enum BillPermissions implements Permission {
    CREATE_BILL("BILL_PERMISSION_1", "Создание счета", "Позволяет создавать счета", "BILL_CREATE"),
    UPDATE_BILL("BILL_PERMISSION_2","Изменение счета", "Позволяет изменять счета", "BILL_UPDATE"),
    REMOVE_BILL("BILL_PERMISSION_3","Удаление счета", "Позволяет удалять счета", "BILL_DELETE"),
    GET_BILL("BILL_PERMISSION_4","Получение счета", "Позволяет получать счета", "BILL_GET"),
    FULL_BILL("BILL_PERMISSION_5","Полный доступ для счета", "Позволяет выполнять любые операции над счетами", "BILL_FULL");

    private String permissionId;

    private String permissionName;

    private String permissionDescription;

    private String permissionSystemValue;

    BillPermissions(String permissionId, String permissionName, String permissionDescription, String permissionSystemValue) {
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
