package com.wp.system.permissions.email;

import com.wp.system.permissions.Permission;

public enum EmailPermissions implements Permission {
    RESEND_EMAIL_VERIFICATION("EMAIL_PERMISSION_1","Переотправка письма подтверждения", "Позволяет переотправлять письма подтверждения", "EMAIL_VERIFICATION_RESEND"),
    SEND_EMAIL_MESSAGE("EMAIL_PERMISSION_2","Отправка писем на почту", "Позволяет отправлять письма на почту", "EMAIL_SEND"),
    SUBMIT_EMAIL_VERIFICATION("EMAIL_PERMISSION_3","Подтверждение почты", "Позволяет позволяет подтверждать почту", "EMAIL_VERIFICATION_SUBMIT"),
    SEND_EMAIL_VERIFICATION("EMAIL_PERMISSION_4","Отправка письма подтверждения", "Позволяет отправлять письма для подтверждения", "EMAIL_VERIFICATION_SEND"),
    FULL_EMAIL("EMAIL_PERMISSION_5","Полный доступ для EMAIL", "Позволяет выполнять любые операции над почтой", "EMAIL_FULL");

    private String permissionId;

    private String permissionName;

    private String permissionDescription;

    private String permissionSystemValue;

    EmailPermissions(String permissionId, String permissionName, String permissionDescription, String permissionSystemValue) {
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
