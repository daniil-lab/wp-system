package com.wp.system.request.auth;

import com.wp.system.other.ValidationErrorMessages;

import javax.validation.constraints.Pattern;

public class PhoneAuthAttemptRequest {
    @Pattern(regexp = "^((\\+7|7|8)+([0-9]){10})$", message = ValidationErrorMessages.PHONE_VALIDATION_FAILED)
    private String phone;

    private String pincode;

    public PhoneAuthAttemptRequest() {};

    public PhoneAuthAttemptRequest(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
