package com.wp.system.dto.logging;

import com.wp.system.entity.logging.SystemErrorLog;

import java.util.UUID;

public class SystemErrorLogDTO {
    private UUID id;

    private String name;

    private String createAt;

    private SystemErrorLogSourceDTO source;

    private String lastTrace;

    public SystemErrorLogDTO() {}

    public SystemErrorLogDTO(SystemErrorLog log) {
        this.id = log.getId();
        this.name = log.getName();
        this.createAt = log.getCreateAt().toString();
        this.source = log.getSource() != null ? new SystemErrorLogSourceDTO(log.getSource()) : null;
        this.lastTrace = log.getLastTrace();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLastTrace() {
        return lastTrace;
    }

    public void setLastTrace(String lastTrace) {
        this.lastTrace = lastTrace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public SystemErrorLogSourceDTO getSource() {
        return source;
    }

    public void setSource(SystemErrorLogSourceDTO source) {
        this.source = source;
    }
}
