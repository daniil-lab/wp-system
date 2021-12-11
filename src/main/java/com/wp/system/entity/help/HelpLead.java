package com.wp.system.entity.help;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class HelpLead {
    @Id
    private UUID id = UUID.randomUUID();

    private String senderPhone;

    private String senderEmail;

    private String content;

    public HelpLead() {};

    public HelpLead(String senderPhone, String senderEmail, String content) {
        this.senderPhone = senderPhone;
        this.senderEmail = senderEmail;
        this.content = content;
    }

    public UUID getId() {
        return id;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
