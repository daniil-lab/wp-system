package com.wp.system.request.user;

import com.wp.system.utils.ValidationErrorMessages;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public class CleanUserRequest {
    @Schema(required = true, description = "Начальная дата, с которой начинать чистку")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private Instant start;

    @Schema(required = true, description = "Конечная дата, с которой заканчивать чистку")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private Instant end;

    public CleanUserRequest() {}

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
}
