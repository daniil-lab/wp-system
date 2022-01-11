package com.wp.system.request.logging;

public class CreateErrorLogRequest {

    private String name;

    private String code;

    private String trace;

    public CreateErrorLogRequest() {}

    public CreateErrorLogRequest(String name, String code, String trace) {
        this.name = name;
        this.code = code;
        this.trace = trace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }
}
