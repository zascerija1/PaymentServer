package ba.unsa.etf.si.payment.request;

import javax.validation.constraints.NotBlank;

public class StaticQRRequest {

    //todo adjust to cash register server...

    @NotBlank
    private Long cashRegisterId;

    @NotBlank
    private Long officeId;

    @NotBlank
    private String businessName;

    public StaticQRRequest(@NotBlank Long cashRegisterId, @NotBlank Long officeId, @NotBlank String businessName) {
        this.cashRegisterId = cashRegisterId;
        this.officeId = officeId;
        this.businessName = businessName;
    }



    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    public Long getCashRegisterId() {
        return cashRegisterId;
    }

    public void setCashRegisterId(Long cashRegisterId) {
        this.cashRegisterId = cashRegisterId;
    }
}
