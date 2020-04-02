package ba.unsa.etf.si.payment.request;

import javax.validation.constraints.NotBlank;

public class DynamicQRRequest {

    //todo adjust to cash register server...

    @NotBlank
    private Long receiptId;

    @NotBlank
    private String businessName;

    @NotBlank
    private String service;

    @NotBlank
    private Double totalPrice;

    @NotBlank
    private Boolean proceed;

    @NotBlank
    private Long bankAccountId;

    public DynamicQRRequest(@NotBlank Long receiptId, @NotBlank String businessName, @NotBlank String service, @NotBlank Double amount, @NotBlank Boolean proceed, Long bankAccountID) {
        this.receiptId = receiptId;
        this.businessName = businessName;
        this.service = service;
        this.totalPrice = amount;
        this.proceed = proceed;
        this.bankAccountId = bankAccountID;
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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean getProceed() {
        return proceed;
    }

    public void setProceed(Boolean proceed) {
        this.proceed = proceed;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }
}