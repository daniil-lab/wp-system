package com.wp.system.dto.logging;

import com.wp.system.entity.logging.ErrorLogSource;

public class SystemErrorLogSourceDTO {
    private String systemName;

    private String displayName;

    public SystemErrorLogSourceDTO() {}

    public SystemErrorLogSourceDTO(ErrorLogSource source) {
        this.systemName = source.name();
        this.displayName = source.getDisplayName();
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
