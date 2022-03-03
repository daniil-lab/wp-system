package com.wp.system.utils.email.sendpulse;

public class SendPulseEmailFile {
    private String fileName;

    private byte[] file;

    public SendPulseEmailFile() {}

    public SendPulseEmailFile(String fileName, byte[] file) {
        this.fileName = fileName;
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}
