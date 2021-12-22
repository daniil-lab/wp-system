package com.wp.system.other.email.local;

import com.dumbster.smtp.SimpleSmtpServer;
import com.wp.system.other.EmailCredData;
import com.wp.system.other.EmailSender;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
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

            Session session = Session.getInstance(mailProps, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("foo@bar.com", "123");
                }
            });

            System.out.println(from.getEmail());
            System.out.println(to.getEmail());

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("developerdaniil@gmail.com"));
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(body);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to.getEmail()));

            Transport.send(msg);
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
