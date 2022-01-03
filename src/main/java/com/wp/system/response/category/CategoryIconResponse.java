package com.wp.system.response.category;

public class CategoryIconResponse {
    private String name;

    private String systemName;

    private String url;

    private String darkUrl;

    public CategoryIconResponse() {}

    public CategoryIconResponse(String name, String systemName, String url, String darkUrl) {
        this.name = name;
        this.systemName = systemName;
        this.url = url;
        this.darkUrl = darkUrl;
    }

    public String getDarkUrl() {
        return darkUrl;
    }

    public void setDarkUrl(String darkUrl) {
        this.darkUrl = darkUrl;
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
}
