package com.wp.system.dto.category;

import com.wp.system.other.CategoryIcon;

public class CategoryDTOIcon {
    private String name;

    private String systemName;

    private String url;

    private String darkUrl;

    public CategoryDTOIcon() {};

    public CategoryDTOIcon(CategoryIcon icon) {
        this.name = icon.getIconName();
        this.systemName = icon.name();
        this.url = "/icon/" + icon.name();
        this.darkUrl = "/icon/dark/" + icon.name();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDarkUrl() {
        return darkUrl;
    }

    public void setDarkUrl(String darkUrl) {
        this.darkUrl = darkUrl;
    }
}
