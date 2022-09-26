package com.wp.system.dto.logging;

import com.wp.system.dto.user.UserDTO;
import com.wp.system.entity.logging.SystemAdminLog;
import com.wp.system.entity.user.User;

public class SystemAdminLogDTO {
    private String action;

    private String description;

    private UserDTO admin;

    private String date;

    public SystemAdminLogDTO() {}

    public SystemAdminLogDTO(SystemAdminLog log) {
        this.admin = log.getUser() != null ? new UserDTO(log.getUser()) : null;
        this.action = log.getAction();
        this.date = log.getDate().toString();
        this.description = log.getDescription();
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

    public UserDTO getAdmin() {
        return admin;
    }

    public void setAdmin(UserDTO admin) {
        this.admin = admin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
