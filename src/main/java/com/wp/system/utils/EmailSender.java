package com.wp.system.utils;

public interface EmailSender {
    boolean sendEmail();

    void addBody(String text);

    void setSubject(String subject);

    void setFrom(String name, String email);

    void setTo(String name, String email);

    void addFile(String fileName, byte[] file);
}
