package com.wp.system.request.image;

import com.wp.system.utils.SystemImageTag;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class UploadImageRequest {
    @NotNull
    private MultipartFile file;

    @NotNull
    private SystemImageTag tag;

    public UploadImageRequest() {}

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public SystemImageTag getTag() {
        return tag;
    }

    public void setTag(SystemImageTag tag) {
        this.tag = tag;
    }
}
