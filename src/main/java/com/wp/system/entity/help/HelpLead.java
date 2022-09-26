package com.wp.system.entity.help;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
public class HelpLead {
    @Id
    private UUID id = UUID.randomUUID();

    private String senderPhone;

    private String senderEmail;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant createAt;

    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long number;

    private HelpLeadStatus status;

    public HelpLead() {};

    public HelpLead(String senderPhone, String senderEmail, String content) {
        this.senderPhone = senderPhone;
        this.senderEmail = senderEmail;
        this.content = content;
        this.createAt = Instant.now();
        this.status = HelpLeadStatus.NEW;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public HelpLeadStatus getStatus() {
        return status;
    }

    public void setStatus(HelpLeadStatus status) {
        this.status = status;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
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
