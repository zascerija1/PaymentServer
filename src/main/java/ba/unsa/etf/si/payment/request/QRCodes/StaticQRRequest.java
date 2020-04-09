package ba.unsa.etf.si.payment.request.QRCodes;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class StaticQRRequest {

    //todo adjust to cash register server...

    //@NotBlank
    @NotNull
    @Min(0)
    private Long cashRegisterId;

    //@NotBlank
    @NotNull
    @Min(0)
    private Long officeId;

    @NotBlank
    private String businessName;

    public StaticQRRequest(Long cashRegisterId, Long officeId, @NotBlank String businessName) {
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
