package com.wp.system.request.auth;

import com.wp.system.other.ValidationErrorMessages;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class SmsSubmitRequest {
    @NotNull
    @Pattern(regexp = "^((\\+7|7|8)+([0-9]){10})$", message = ValidationErrorMessages.PHONE_VALIDATION_FAILED)
    private String phone;

    public SmsSubmitRequest() {}

    public SmsSubmitRequest(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
