package ba.unsa.etf.si.payment.request;

import javax.validation.constraints.NotBlank;

public class DynamicQRRequest {

    //todo adjust to cash register server...

    @NotBlank
    private Long cashRegisterBankAccountID;

    @NotBlank
    private String businessName;

    @NotBlank
    private String service;

    @NotBlank
    private Double amount;

    @NotBlank
    private Boolean proceed;

    private Long bankAccountID;

    public DynamicQRRequest(@NotBlank Long cashRegisterBankAccountID, @NotBlank String businessName, @NotBlank String service, @NotBlank Double amount, @NotBlank Boolean proceed, Long bankAccountID) {
        this.cashRegisterBankAccountID = cashRegisterBankAccountID;
        this.businessName = businessName;
        this.service = service;
        this.amount = amount;
        this.proceed = proceed;
        this.bankAccountID = bankAccountID;
    }

    public Long getCashRegisterBankAccountID() {
        return cashRegisterBankAccountID;
    }

    public void setCashRegisterBankAccountID(Long cashRegisterBankAccountID) {
        this.cashRegisterBankAccountID = cashRegisterBankAccountID;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean getProceed() {
        return proceed;
    }

    public void setProceed(Boolean proceed) {
        this.proceed = proceed;
    }

    public Long getBankAccountID() {
        return bankAccountID;
    }

    public void setBankAccountID(Long bankAccountID) {
        this.bankAccountID = bankAccountID;
    }
}