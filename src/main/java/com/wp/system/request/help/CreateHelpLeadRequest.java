package com.wp.system.request.help;

import com.wp.system.other.ValidationErrorMessages;

import javax.validation.constraints.Pattern;

public class CreateHelpLeadRequest {

    @Pattern(regexp = "^((\\+7|7|8)+([0-9]){10})$", message = ValidationErrorMessages.PHONE_VALIDATION_FAILED)
    private String phone;

    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = ValidationErrorMessages.EMAIL_VALIDATION_FAILED)
    private String email;

    private String content;

    public CreateHelpLeadRequest() {}

    public CreateHelpLeadRequest(String phone, String email, String content) {
        this.phone = phone;
        this.email = email;
        this.content = content;
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
