package com.wp.system.request.bill;

import com.wp.system.utils.ValidationErrorMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

public class EditBillRequest {

    @Schema(required = false, description = "Название счета")
    @Length(min = 4, max = 64, message = ValidationErrorMessages.INVALID_BILL_NAME)
    private String name;

    private Integer newAmount;

    private Integer newCents;

    public EditBillRequest() {}

    public Integer getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(Integer newAmount) {
        this.newAmount = newAmount;
    }

    public Integer getNewCents() {
        return newCents;
    }

    public void setNewCents(Integer newCents) {
        this.newCents = newCents;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
