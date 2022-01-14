package com.wp.system.request.logging;

import com.wp.system.entity.logging.ErrorLogSource;

import javax.validation.constraints.NotNull;

public class CreateErrorLogRequest {
    @NotNull
    private String name;

    @NotNull
    private ErrorLogSource source;

    private String additional;

    public CreateErrorLogRequest() {}

    public CreateErrorLogRequest(String name, ErrorLogSource source) {
        this.name = name;
        this.source = source;
    }

    public CreateErrorLogRequest(String name, ErrorLogSource source, String additional) {
        this.name = name;
        this.source = source;
        this.additional = additional;
    }

    public ErrorLogSource getSource() {
        return source;
    }

    public void setSource(ErrorLogSource source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }
}
