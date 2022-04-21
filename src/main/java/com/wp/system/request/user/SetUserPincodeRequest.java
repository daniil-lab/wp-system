package com.wp.system.request.user;

import com.wp.system.utils.ValidationErrorMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class SetUserPincodeRequest {
    @Schema(required = true, description = "PIN-CODE пользователя")
    @Length(min = 4, max = 4, message = ValidationErrorMessages.PINCODE_VALIDATION_FAILED)
    private String code;

    public SetUserPincodeRequest() {};

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
