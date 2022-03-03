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

    @Schema(required = true, description = "ID пользователя")
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
