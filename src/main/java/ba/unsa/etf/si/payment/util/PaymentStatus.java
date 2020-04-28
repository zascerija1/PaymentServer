package ba.unsa.etf.si.payment.util;

public enum PaymentStatus {
    SUFFICIENT_FUNDS ("SUFFICIENT_FUNDS"),
    PROBLEM ("PROBLEM"),
    CANCELED ("CANCELED"),
    PAID ("PAID"),
    INSUFFICIENT_FUNDS ("INSUFFICIENT_FUNDS"),
    PENDING("PENDING");

    private String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
