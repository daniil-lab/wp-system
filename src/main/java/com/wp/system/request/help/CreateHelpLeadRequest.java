package com.wp.system.request.help;

import com.wp.system.utils.ValidationErrorMessages;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CreateHelpLeadRequest {

    @Schema(required = true, description = "Номер телефона заявителя")
    @Pattern(regexp = "^((\\+7)+([0-9]){10})$", message = ValidationErrorMessages.PHONE_VALIDATION_FAILED)
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private String phone;

    @Schema(required = true, description = "Электронная почта заявителя")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = ValidationErrorMessages.EMAIL_VALIDATION_FAILED)
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
    private String email;

    @Schema(required = true, description = "Текст сообщения")
    @NotNull(message = ValidationErrorMessages.NO_EMPTY)
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
