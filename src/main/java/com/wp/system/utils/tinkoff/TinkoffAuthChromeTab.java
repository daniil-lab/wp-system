package com.wp.system.utils.tinkoff;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import org.openqa.selenium.WebDriver;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class TinkoffAuthChromeTab {
    private UUID id = UUID.randomUUID();

    @JsonIgnore
    @JsonIgnoreProperties
    private WebDriver driver;

    private UUID userId;

    private Instant expiredAt;

    private String phone;

    private Instant exportStartDate;

    private String password;

    public TinkoffAuthChromeTab() {}

    public TinkoffAuthChromeTab(WebDriver driver) {
        this.expiredAt = Instant.now().plus(15, ChronoUnit.MINUTES);
        this.driver = driver;
    }

    public Instant getExportStartDate() {
        return exportStartDate;
    }

    public void setExportStartDate(Instant exportStartDate) {
        this.exportStartDate = exportStartDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Instant getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Instant expiredAt) {
        this.expiredAt = expiredAt;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
