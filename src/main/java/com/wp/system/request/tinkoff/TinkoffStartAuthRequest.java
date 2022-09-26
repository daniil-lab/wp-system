package com.wp.system.request.tinkoff;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public class TinkoffStartAuthRequest {
    private String phone;

    private String password;

    @NotNull
    private boolean reAuth;

    private Instant exportStartDate;

    public TinkoffStartAuthRequest() {}

    public Instant getExportStartDate() {
        return exportStartDate;
    }

    public void setExportStartDate(Instant exportStartDate) {
        this.exportStartDate = exportStartDate;
    }

    public boolean isReAuth() {
        return reAuth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setReAuth(boolean reAuth) {
        this.reAuth = reAuth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
