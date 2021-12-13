package com.wp.system.request.bill;

public class EditBillRequest {

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
