package com.wp.system.request.user;

import com.wp.system.other.ValidationErrorMessages;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

public class SetUserPincodeRequest {
    @Length(min = 4, max = 4, message = ValidationErrorMessages.PINCODE_VALIDATION_FAILED)
    private String code;

    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private UUID userId;

    public SetUserPincodeRequest() {};

    public SetUserPincodeRequest(String code, UUID userId) {
        this.code = code;
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
