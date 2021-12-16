package com.wp.system.other;

public interface EmailSender {
    boolean sendEmail();

    void setContentType(EmailContentType type);

    void addBody(String text);

    void setSubject(String subject);

    void setFrom(String from);

    void setTo(String to);

    void addFile();
}
