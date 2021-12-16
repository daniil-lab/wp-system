package com.wp.system.other;

import org.springframework.http.MediaType;

import javax.print.attribute.standard.Media;

public enum CategoryIcon {
    HOME("./images/Home.svg", "Быт", MediaType.valueOf("image/svg+xml"), "./images/Home.svg");

    private String iconPath;

    private String iconName;

    private MediaType mediaType;

    private String darkIconPath;

    CategoryIcon(String iconPath, String iconName, MediaType mediaType, String darkIconPath) {
        this.iconPath = iconPath;
        this.iconName = iconName;
        this.mediaType = mediaType;
        this.darkIconPath = darkIconPath;
    }

    public String getDarkIconPath() {
        return darkIconPath;
    }

    public void setDarkIconPath(String darkIconPath) {
        this.darkIconPath = darkIconPath;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}
