package com.wp.system.dto.help;

import com.wp.system.entity.help.HelpLead;

import java.util.UUID;

public class HelpLeadDTO {
    private UUID id;

    private String phone;

    private String email;

    private String content;

    public HelpLeadDTO() {}

    public HelpLeadDTO(HelpLead lead) {
        if(lead == null)
            return;

        this.id = lead.getId();
        this.phone = lead.getSenderPhone();
        this.email = lead.getSenderEmail();
        this.content = lead.getContent();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
