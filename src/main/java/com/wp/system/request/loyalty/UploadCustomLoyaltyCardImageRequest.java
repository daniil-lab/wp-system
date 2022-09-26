package com.wp.system.request.loyalty;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class UploadCustomLoyaltyCardImageRequest {
    @NotNull
    private MultipartFile file;

    public UploadCustomLoyaltyCardImageRequest() {
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
