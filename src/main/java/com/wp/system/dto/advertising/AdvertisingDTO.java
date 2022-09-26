package com.wp.system.dto.advertising;

import com.wp.system.entity.advertising.Advertising;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AdvertisingDTO {
    private UUID id;

    private String title;

    private String subTitle;

    private String content;

    private List<AdvertisingFileDTO> files;

    public AdvertisingDTO() {}

    public AdvertisingDTO(Advertising advertising) {
        if(advertising == null)
            return;

        this.id = advertising.getId();
        this.title = advertising.getTitle();
        this.subTitle = advertising.getSubTitle();
        this.content = advertising.getContent();
        this.files = advertising.getFiles().stream().map(AdvertisingFileDTO::new).collect(Collectors.toList());
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

    public List<AdvertisingFileDTO> getFiles() {
        return files;
    }

    public void setFiles(List<AdvertisingFileDTO> files) {
        this.files = files;
    }
}
