package ba.unsa.etf.si.payment.util.NotificationUtil;

public enum NotificationStatus {
    INFO ("INFO"),
    WARNING("WARNING"),
    ERROR("ERROR");

    private String status;

    private NotificationStatus(String status){
        this.status=status;
    }

    public String getStatus () {
        return status;
    }

}