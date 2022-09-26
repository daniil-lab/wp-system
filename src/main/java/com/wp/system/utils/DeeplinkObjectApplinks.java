package com.wp.system.utils;

import java.util.ArrayList;
import java.util.List;

public class DeeplinkObjectApplinks {
    private List<String> apps = new ArrayList<>();

    private List<DeeplinkObjectApplinksDetails> details = List.of(new DeeplinkObjectApplinksDetails());

    public DeeplinkObjectApplinks() {}

    public List<String> getApps() {
        return apps;
    }

    public void setApps(List<String> apps) {
        this.apps = apps;
    }

    public List<DeeplinkObjectApplinksDetails> getDetails() {
        return details;
    }

    public void setDetails(List<DeeplinkObjectApplinksDetails> details) {
        this.details = details;
    }
}
