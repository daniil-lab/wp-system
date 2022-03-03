package com.wp.system.utils.tinkoff;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import org.openqa.selenium.WebDriver;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

public class TinkoffAuthChromeTab {
    private UUID id = UUID.randomUUID();

    @JsonIgnore
    @JsonIgnoreProperties
    private WebDriver driver;

    private UUID userId;

    public TinkoffAuthChromeTab() {}

    public TinkoffAuthChromeTab(WebDriver driver) {
        this.driver = driver;
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
}
