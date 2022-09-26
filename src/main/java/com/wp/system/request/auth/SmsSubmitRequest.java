package com.wp.system.request.auth;

import com.wp.system.utils.ValidationErrorMessages;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class SmsSubmitRequest {
    @Schema(required = true, description = "Номер телефона")
    @NotNull
    @Pattern(regexp = "^((\\+7)+([0-9]){10})$", message = ValidationErrorMessages.PHONE_VALIDATION_FAILED)
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
