package ba.unsa.etf.si.payment.response;

public class MerchantDataResponse {

    private Long merchantId;
    private String merchantName;

    public MerchantDataResponse(Long merchantId, String merchantName) {
        this.merchantId = merchantId;
        this.merchantName = merchantName;
    }

    public MerchantDataResponse() {
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}
