package com.wp.system.request.advertising;

import org.springframework.web.multipart.MultipartFile;

public class AddFileToAdvertisingRequest {
    private MultipartFile file;

    public AddFileToAdvertisingRequest() {}

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
