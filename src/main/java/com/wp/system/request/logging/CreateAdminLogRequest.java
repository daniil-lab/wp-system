package com.wp.system.request.logging;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CreateAdminLogRequest {
    @NotNull
    private UUID adminId;

    @NotNull
    private String action;

    @NotNull
    private String description;

    public CreateAdminLogRequest() {}

    public CreateAdminLogRequest(UUID adminId, String action, String description) {
        this.adminId = adminId;
        this.action = action;
        this.description = description;
    }

    public UUID getAdminId() {
        return adminId;
    }

    public void setAdminId(UUID adminId) {
        this.adminId = adminId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
