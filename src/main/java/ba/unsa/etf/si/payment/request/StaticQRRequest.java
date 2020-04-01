package ba.unsa.etf.si.payment.request;

import javax.validation.constraints.NotBlank;

public class StaticQRRequest {

    //todo adjust to cash register server...

    @NotBlank
    private Long cashRegisterBankAccountID;

    @NotBlank
    private String businessName;

    public StaticQRRequest(@NotBlank Long cashRegisterBankAccountID, @NotBlank String businessName) {
        this.cashRegisterBankAccountID = cashRegisterBankAccountID;
        this.businessName = businessName;
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
}
