package com.wp.system.request.user;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public class ExportDataRequest {

    @NotNull
    private Instant start;

    @NotNull
    private Instant end;

    @NotNull
    private UUID userId;

    public ExportDataRequest() {}

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
