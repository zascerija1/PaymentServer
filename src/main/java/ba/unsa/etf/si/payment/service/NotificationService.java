package ba.unsa.etf.si.payment.service;

import ba.unsa.etf.si.payment.exception.ResourceNotFoundException;
import ba.unsa.etf.si.payment.model.Notification;
import ba.unsa.etf.si.payment.repository.NotificationRepository;
import ba.unsa.etf.si.payment.response.NotificationResponse;
import ba.unsa.etf.si.payment.util.NotificationUtil.NotificationHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationHandler notificationHandler;

    public NotificationService(NotificationRepository notificationRepository, NotificationHandler notificationHandler) {
        this.notificationRepository = notificationRepository;
        this.notificationHandler = notificationHandler;
    }

    public Notification save(Notification notification){
        return notificationRepository.save(notification);
    }

    public List<NotificationResponse> getAllUserNotifications(Long applicationUserId){
        return notificationRepository
                .findAllByApplicationUser_Id(applicationUserId)
                .stream()
                .map(notificationHandler::buildNotificationResponse)
                .collect(Collectors.toList());
    }

    public NotificationResponse getOneUserNotification (UUID notificationId, Long userId){
        Notification notification=notificationRepository.findByIdAndApplicationUser_Id(notificationId,userId);
        if(notification==null){
            throw new ResourceNotFoundException("Wrong notificationId");
        }
        notification.setRead(true);
        notificationRepository.save(notification);
        return notificationHandler.buildNotificationResponse(notification);
    }
}
