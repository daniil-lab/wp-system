package com.wp.system.utils.acquiring.tinkoff;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class InitPaymentReceipt {
    @JsonProperty("Email")
    private String Email;

    @JsonProperty("Phone")
    private String Phone;

    @JsonProperty("EmailCompany")
    private String EmailCompany;

    @JsonProperty("Taxation")
    private String Taxation = "usn_income";

    @JsonProperty("Items")
    private List<InitPaymentItem> Items;

    public InitPaymentReceipt() {}

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmailCompany() {
        return EmailCompany;
    }

    public void setEmailCompany(String emailCompany) {
        EmailCompany = emailCompany;
    }

    public String getTaxation() {
        return Taxation;
    }

    public void setTaxation(String taxation) {
        Taxation = taxation;
    }

    public List<InitPaymentItem> getItems() {
        return Items;
    }

    public void setItems(List<InitPaymentItem> items) {
        Items = items;
    }
}
