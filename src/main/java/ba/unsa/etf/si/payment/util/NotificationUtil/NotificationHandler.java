package ba.unsa.etf.si.payment.util.NotificationUtil;

import ba.unsa.etf.si.payment.model.Notification;
import ba.unsa.etf.si.payment.response.NotificationResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationHandler {
    
    private final SimpMessagingTemplate simpMessagingTemplate;

    public NotificationHandler(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }


    public NotificationResponse buildNotificationResponse(Notification notification){
        return new NotificationResponse(notification.getId(),
                notification.getSubjectId(), notification.getNotificationMessage(),
                notification.getNotificationStatus(), notification.getNotificationType(), notification.getCreatedAt());
    }

    public void sendNotification (Notification notification){
        NotificationResponse notificationResponse = buildNotificationResponse(notification);
        simpMessagingTemplate.convertAndSend( "/queue/reply/"+notification.getApplicationUser().getUsername(), notificationResponse);
    }
}
