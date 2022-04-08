package com.wp.system.utils.tochka.response;

public class TochkaTransactionResponse {
    private String counterparty_account_number;

    private String counterparty_bank_bic;

    private String counterparty_bank_name;

    private String counterparty_inn;

    private String counterparty_kpp;

    private String counterparty_name;

    private String operation_type;

    private String payment_amount;

    private String payment_bank_system_id;

    private String payment_charge_date;

    private String payment_date;

    private String payment_number;

    private String payment_purpose;

    private String supplier_bill_id;

    private String tax_info_document_date;

    private String tax_info_document_number;

    private String tax_info_kbk;

    private String tax_info_okato;

    private String tax_info_period;

    private String tax_info_reason_code;

    private String tax_info_status;

    private String x_payment_id;

    public TochkaTransactionResponse() {}

    @Override
    public String toString() {
        return "TochkaTransactionResponse{" +
                "counterparty_account_number='" + counterparty_account_number + '\'' +
                ", counterparty_bank_bic='" + counterparty_bank_bic + '\'' +
                ", counterparty_bank_name='" + counterparty_bank_name + '\'' +
                ", counterparty_inn='" + counterparty_inn + '\'' +
                ", counterparty_kpp='" + counterparty_kpp + '\'' +
                ", counterparty_name='" + counterparty_name + '\'' +
                ", operation_type='" + operation_type + '\'' +
                ", payment_amount='" + payment_amount + '\'' +
                ", payment_bank_system_id='" + payment_bank_system_id + '\'' +
                ", payment_charge_date='" + payment_charge_date + '\'' +
                ", payment_date='" + payment_date + '\'' +
                ", payment_number='" + payment_number + '\'' +
                ", payment_purpose='" + payment_purpose + '\'' +
                ", supplier_bill_id='" + supplier_bill_id + '\'' +
                ", tax_info_document_date='" + tax_info_document_date + '\'' +
                ", tax_info_document_number='" + tax_info_document_number + '\'' +
                ", tax_info_kbk='" + tax_info_kbk + '\'' +
                ", tax_info_okato='" + tax_info_okato + '\'' +
                ", tax_info_period='" + tax_info_period + '\'' +
                ", tax_info_reason_code='" + tax_info_reason_code + '\'' +
                ", tax_info_status='" + tax_info_status + '\'' +
                ", x_payment_id='" + x_payment_id + '\'' +
                '}';
    }

    public String getCounterparty_account_number() {
        return counterparty_account_number;
    }

    public void setCounterparty_account_number(String counterparty_account_number) {
        this.counterparty_account_number = counterparty_account_number;
    }

    public String getCounterparty_bank_bic() {
        return counterparty_bank_bic;
    }

    public void setCounterparty_bank_bic(String counterparty_bank_bic) {
        this.counterparty_bank_bic = counterparty_bank_bic;
    }

    public String getCounterparty_bank_name() {
        return counterparty_bank_name;
    }

    public void setCounterparty_bank_name(String counterparty_bank_name) {
        this.counterparty_bank_name = counterparty_bank_name;
    }

    public String getCounterparty_inn() {
        return counterparty_inn;
    }

    public void setCounterparty_inn(String counterparty_inn) {
        this.counterparty_inn = counterparty_inn;
    }

    public String getCounterparty_kpp() {
        return counterparty_kpp;
    }

    public void setCounterparty_kpp(String counterparty_kpp) {
        this.counterparty_kpp = counterparty_kpp;
    }

    public String getCounterparty_name() {
        return counterparty_name;
    }

    public void setCounterparty_name(String counterparty_name) {
        this.counterparty_name = counterparty_name;
    }

    public String getOperation_type() {
        return operation_type;
    }

    public void setOperation_type(String operation_type) {
        this.operation_type = operation_type;
    }

    public String getPayment_amount() {
        return payment_amount;
    }

    public void setPayment_amount(String payment_amount) {
        this.payment_amount = payment_amount;
    }

    public String getPayment_bank_system_id() {
        return payment_bank_system_id;
    }

    public void setPayment_bank_system_id(String payment_bank_system_id) {
        this.payment_bank_system_id = payment_bank_system_id;
    }

    public String getPayment_charge_date() {
        return payment_charge_date;
    }

    public void setPayment_charge_date(String payment_charge_date) {
        this.payment_charge_date = payment_charge_date;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public String getPayment_number() {
        return payment_number;
    }

    public void setPayment_number(String payment_number) {
        this.payment_number = payment_number;
    }

    public String getPayment_purpose() {
        return payment_purpose;
    }

    public void setPayment_purpose(String payment_purpose) {
        this.payment_purpose = payment_purpose;
    }

    public String getSupplier_bill_id() {
        return supplier_bill_id;
    }

    public void setSupplier_bill_id(String supplier_bill_id) {
        this.supplier_bill_id = supplier_bill_id;
    }

    public String getTax_info_document_date() {
        return tax_info_document_date;
    }

    public void setTax_info_document_date(String tax_info_document_date) {
        this.tax_info_document_date = tax_info_document_date;
    }

    public String getTax_info_document_number() {
        return tax_info_document_number;
    }

    public void setTax_info_document_number(String tax_info_document_number) {
        this.tax_info_document_number = tax_info_document_number;
    }

    public String getTax_info_kbk() {
        return tax_info_kbk;
    }

    public void setTax_info_kbk(String tax_info_kbk) {
        this.tax_info_kbk = tax_info_kbk;
    }

    public String getTax_info_okato() {
        return tax_info_okato;
    }

    public void setTax_info_okato(String tax_info_okato) {
        this.tax_info_okato = tax_info_okato;
    }

    public String getTax_info_period() {
        return tax_info_period;
    }

    public void setTax_info_period(String tax_info_period) {
        this.tax_info_period = tax_info_period;
    }

    public String getTax_info_reason_code() {
        return tax_info_reason_code;
    }

    public void setTax_info_reason_code(String tax_info_reason_code) {
        this.tax_info_reason_code = tax_info_reason_code;
    }

    public String getTax_info_status() {
        return tax_info_status;
    }

    public void setTax_info_status(String tax_info_status) {
        this.tax_info_status = tax_info_status;
    }

    public String getX_payment_id() {
        return x_payment_id;
    }

    public void setX_payment_id(String x_payment_id) {
        this.x_payment_id = x_payment_id;
    }
}
