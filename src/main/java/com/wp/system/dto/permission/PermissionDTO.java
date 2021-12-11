package com.wp.system.dto.permission;

import com.wp.system.permissions.Permission;

public class PermissionDTO {
    private String name;

    private String description;

    private String systemValue;

    public PermissionDTO() {};

    public PermissionDTO(Permission permission) {
        this.name = permission.getPermissionName();
        this.description = permission.getPermissionDescription();
        this.systemValue = permission.getPermissionSystemValue();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSystemValue() {
        return systemValue;
    }

    public void setSystemValue(String systemValue) {
        this.systemValue = systemValue;
    }
}
