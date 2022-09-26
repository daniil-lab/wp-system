package com.wp.system.entity.logging;

import com.wp.system.exception.ServiceException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

@Entity
public class SystemErrorLog {
    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant createAt;

    private ErrorLogSource source;

    private String additional;

    @Column(columnDefinition="TEXT")
    private String lastTrace;

    public SystemErrorLog() {}

    public SystemErrorLog(ServiceException error) {
        this.name = error.getMessage();
        this.createAt = Instant.now();
        this.lastTrace = error.getLastTrace();
        this.additional = error.getDescription();
    }

    public SystemErrorLog(String name, ErrorLogSource source, String additional) {
        this.name = name;
        this.source = source;
        this.additional = additional;
        this.createAt = Instant.now();
    }

    public SystemErrorLog(String name, ErrorLogSource source) {
        this.name = name;
        this.source = source;
        this.createAt = Instant.now();
    }

    public String getLastTrace() {
        return lastTrace;
    }

    public void setLastTrace(String lastTrace) {
        this.lastTrace = lastTrace;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public ErrorLogSource getSource() {
        return source;
    }

    public void setSource(ErrorLogSource source) {
        this.source = source;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
