package com.wp.system.entity.advertising;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
public class Advertising {
    @Id
    private UUID id = UUID.randomUUID();

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String subTitle;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<AdvertisingFile> files = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<AdvertisingView> views = new HashSet<>();

    public Advertising() {}

    public void addFile(AdvertisingFile file) {
        this.files.add(file);
    }

    public void removeFile(AdvertisingFile file) {
        this.files.remove(file);
    }

    public void addView(AdvertisingView view) {
        this.views.add(view);
    }

    public void removeView(AdvertisingView view) {
        this.views.remove(view);
    }

    public Set<AdvertisingFile> getFiles() {
        return files;
    }

    public void setFiles(Set<AdvertisingFile> files) {
        this.files = files;
    }

    public Set<AdvertisingView> getViews() {
        return views;
    }

    public void setViews(Set<AdvertisingView> views) {
        this.views = views;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
