package ba.unsa.etf.si.payment.response;

import ba.unsa.etf.si.payment.util.NotificationUtil.NotificationStatus;
import ba.unsa.etf.si.payment.util.NotificationUtil.NotificationType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.UUID;

public class NotificationResponse {

    private UUID notificationId;
    private String subjectId;
    private String message;
    private NotificationStatus notificationStatus;
    private NotificationType notificationType;
    @JsonFormat(pattern="dd.MM.yyyy HH:mm:ss",timezone="Europe/Sarajevo")
    private Date notificationDateAndTime;

    public NotificationResponse() {
    }

    public NotificationResponse(UUID id, String subjectId, String message, NotificationStatus notificationStatus, NotificationType notificationType, Date notificationDateAndTime) {
        this.notificationId = id;
        this.subjectId = subjectId;
        this.message = message;
        this.notificationStatus = notificationStatus;
        this.notificationType = notificationType;
        this.notificationDateAndTime = notificationDateAndTime;
    }

    public UUID getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(UUID notificationId) {
        this.notificationId = notificationId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(NotificationStatus notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public Date getNotificationDateAndTime() {
        return notificationDateAndTime;
    }

    public void setNotificationDateAndTime(Date notificationDateAndTime) {
        this.notificationDateAndTime = notificationDateAndTime;
    }
}
