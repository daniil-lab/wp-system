package com.wp.system.permissions.bill;

import com.wp.system.permissions.Permission;

public enum BillTransactionPermissions implements Permission {
    GET_BILL_TRANSACTION("BILL_TRANSACTION_PERMISSION_1","Получение транзакций", "Позволяет получать транзакции", "BILL_TRANSACTION_GET"),
    UPDATE_BILL_TRANSACTION("BILL_TRANSACTION_PERMISSION_2","Изменение транзакций", "Позволяет изменять транзакции", "BILL_TRANSACTION_UPDATE"),
    FULL_BILL_TRANSACTION("BILL_TRANSACTION_PERMISSION_3","Полный доступ для транзакций", "Позволяет выполнять любые операции над транзакциями", "BILL_TRANSACTION_FULL");

    private String permissionId;

    private String permissionName;

    private String permissionDescription;

    private String permissionSystemValue;

    BillTransactionPermissions(String permissionId, String permissionName, String permissionDescription, String permissionSystemValue) {
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
