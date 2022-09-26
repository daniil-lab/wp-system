package com.wp.system.utils.email.local;

import com.wp.system.utils.EmailCredData;
import com.wp.system.utils.EmailSender;

import java.util.Properties;

public class LocalMailSender implements EmailSender {

    private String body;

    private String subject;

    private EmailCredData from;

    private EmailCredData to;

    public LocalMailSender() {
        this.from = new EmailCredData();
        this.to = new EmailCredData();
    };

    @Override
    public boolean sendEmail() {
        try {
            Properties mailProps = new Properties();

            mailProps.setProperty("mail.smtp.host", "localhost");
            mailProps.setProperty("mail.smtp.port", "25");
            mailProps.setProperty("mail.smtp.auth", String.valueOf(true));

            System.out.println(from.getEmail());
            System.out.println(to.getEmail());

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return false;
    }

    @Override
    public void addBody(String text) {
        this.body = text;
    }

    @Override
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public void setFrom(String name, String email) {
        this.from.setName(name);
        this.from.setEmail(email);
    }

    @Override
    public void setTo(String name, String email) {
        this.to.setName(name);
        this.to.setEmail(email);
    }

    @Override
    public void addFile(String fileName, byte[] file) {

    }
}
