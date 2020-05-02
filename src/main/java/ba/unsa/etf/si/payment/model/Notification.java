package ba.unsa.etf.si.payment.model;

import ba.unsa.etf.si.payment.util.NotificationUtil.NotificationStatus;
import ba.unsa.etf.si.payment.util.NotificationUtil.NotificationType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.UUID;
//todo ovo ce biti model probably

@Entity
@Table(name = "notifications")
public class Notification extends AuditModel {

    @Id
    @GeneratedValue(generator = "UUID3")
    @GenericGenerator(
            name = "UUID3",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    private NotificationStatus notificationStatus;

    private NotificationType notificationType;

    private String subjectId;

    @Column(columnDefinition = "text")
    private String notificationMessage;

    //Lazy cause we do not need to fetch user with notification
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "application_user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ApplicationUser applicationUser;

    private Boolean read;


    public Notification() {
    }

    public Notification(NotificationStatus notificationStatus, NotificationType notificationType, String subjectId, String notificationMessage, ApplicationUser applicationUser) {
        this.notificationStatus = notificationStatus;
        this.notificationType = notificationType;
        this.subjectId = subjectId;
        this.notificationMessage = notificationMessage;
        this.applicationUser=applicationUser;
        this.read = false;
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

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }
}