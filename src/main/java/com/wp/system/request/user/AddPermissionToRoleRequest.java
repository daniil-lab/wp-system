package com.wp.system.request.user;

import com.wp.system.utils.ValidationErrorMessages;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class AddPermissionToRoleRequest {
    @Schema(required = true, description = "Системное название доступа")
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
