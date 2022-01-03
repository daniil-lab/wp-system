package com.wp.system.permissions.loyalty;

import com.wp.system.permissions.Permission;

public enum LoyaltyBlankPermissions implements Permission {
    CREATE_LOYALTY_BLANK("LOYALTY_BLANK_PERMISSION_1", "Создание бланка лояльности", "Позволяет создавать бланки лояльности", "LOYALTY_BLANK_CREATE"),
    UPDATE_LOYALTY_BLANK("LOYALTY_BLANK_PERMISSION_2","Изменение бланка лояльности", "Позволяет изменять бланки лояльности", "LOYALTY_BLANK_UPDATE"),
    REMOVE_LOYALTY_BLANK("LOYALTY_BLANK_PERMISSION_3","Удаление бланка лояльности", "Позволяет удалять бланки лояльности", "LOYALTY_BLANK_DELETE"),
    GET_LOYALTY_BLANK("LOYALTY_BLANK_PERMISSION_4","Получение бланков лояльности", "Позволяет получать бланки лояльности", "LOYALTY_BLANK_GET"),
    FULL_LOYALTY_BLANK("LOYALTY_BLANK_PERMISSION_5","Полный доступ для бланков лояльности", "Позволяет выполнять любые операции над бланками лояльности", "LOYALTY_BLANK_FULL");

    private String permissionId;

    private String permissionName;

    private String permissionDescription;

    private String permissionSystemValue;

    LoyaltyBlankPermissions(String permissionId, String permissionName, String permissionDescription, String permissionSystemValue) {
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
