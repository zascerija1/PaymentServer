package ba.unsa.etf.si.payment.util.NotificationUtil;

public enum NotificationType {
    MONEY_TRANSFER ("MONEY_TRANSFER"),
    TRANSACTION ("TRANSACTION"),
    ACCOUNT_BALANCE("ACCOUNT_BALANCE");
    private String type;

    NotificationType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}