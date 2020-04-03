package ba.unsa.etf.si.payment.request.QRCodes;

import javax.validation.constraints.NotBlank;

public class DynamicQRRequest {

    //todo adjust to cash register server...

    @NotBlank
    private String receiptId;

    @NotBlank
    private String businessName;

    @NotBlank
    private String service;

    private Double totalPrice;

    private Boolean proceed;

    private Long bankAccountId;

    public DynamicQRRequest(@NotBlank String receiptId, @NotBlank String businessName, @NotBlank String service,  Double amount, Boolean proceed, Long bankAccountID) {
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

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }
}