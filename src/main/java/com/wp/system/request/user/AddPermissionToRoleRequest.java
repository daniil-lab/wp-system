package com.wp.system.request.user;

import com.wp.system.other.ValidationErrorMessages;

import javax.validation.constraints.NotNull;

public class AddPermissionToRoleRequest {
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private String systemName;

    public AddPermissionToRoleRequest() {};

    public AddPermissionToRoleRequest(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
}
