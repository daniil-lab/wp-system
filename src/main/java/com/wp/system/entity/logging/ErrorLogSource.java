package com.wp.system.entity.logging;

public enum ErrorLogSource {
    BACK("Серверная часть"),
    IOS("IOS приложение"),
    ANDROID("Android приложение"),
    FRONT("Веб интерфейс");

    private String displayName;

    ErrorLogSource(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
