package com.wp.system.entity;

import java.io.Serializable;
import java.util.List;

public class PublicData implements Serializable {
    private String website;

    private String confPolicy;

    private String instagram;

    private String vk;

    private String facebook;

    private String youtube;

    private List<String> phoneNumbers;

    private List<String> emails;

    public PublicData() {}

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getConfPolicy() {
        return confPolicy;
    }

    public void setConfPolicy(String confPolicy) {
        this.confPolicy = confPolicy;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getVk() {
        return vk;
    }

    public void setVk(String vk) {
        this.vk = vk;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }
}
