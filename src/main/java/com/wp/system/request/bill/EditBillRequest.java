package com.wp.system.request.bill;

import com.wp.system.utils.ValidationErrorMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

public class EditBillRequest {

    @Schema(required = false, description = "Название счета")
    @Length(min = 4, max = 64, message = ValidationErrorMessages.INVALID_BILL_NAME)
    private String name;

    public EditBillRequest() {}

    public EditBillRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
