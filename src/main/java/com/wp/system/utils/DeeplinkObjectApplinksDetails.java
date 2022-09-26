package com.wp.system.utils;

import java.util.List;

public class DeeplinkObjectApplinksDetails {
    private String appID = "3GBWH35VG2.walletbox.app";

    private List<String> paths = List.of("/api/v1/tochka/auth-hook");

    public DeeplinkObjectApplinksDetails() {
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }
}
