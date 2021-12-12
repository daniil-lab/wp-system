package com.wp.system.permissions.help;

import com.wp.system.permissions.Permission;

public enum HelpLeadPermissions implements Permission {
    REMOVE_HELP_LEAD("HELP_LEAD_PERMISSION_1","Удаление обращений о помощи", "Позволяет удалять обращения о помощи", "HELP_LEAD_DELETE"),
    GET_HELP_LEAD("HELP_LEAD_PERMISSION_2","Получение обращений о помощи", "Позволяет получать обращения о помощи", "HELP_LEAD_GET"),
    FULL_HELP_LEAD("HELP_LEAD_PERMISSION_3","Полный доступ для обращений о помощи", "Позволяет выполнять любые операции над обращениями о помощи", "HELP_LEAD_FULL");

    private String permissionId;

    private String permissionName;

    private String permissionDescription;

    private String permissionSystemValue;

    HelpLeadPermissions(String permissionId, String permissionName, String permissionDescription, String permissionSystemValue) {
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
