package ba.unsa.etf.si.payment.controller;

import ba.unsa.etf.si.payment.response.NotificationResponse;
import ba.unsa.etf.si.payment.security.CurrentUser;
import ba.unsa.etf.si.payment.security.UserPrincipal;
import ba.unsa.etf.si.payment.service.NotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/all")
    public List<NotificationResponse> getAllNotifications(@CurrentUser UserPrincipal currentUser){
        return notificationService.getAllUserNotifications(currentUser.getId());
    }

    @GetMapping("/unread")
    public List<NotificationResponse> getUnreadNotifications(@CurrentUser UserPrincipal currentUser){
        return notificationService.getAllUnreadUserNotifications(currentUser.getId());
    }
    @GetMapping("/specific/{notificationId}")
    public NotificationResponse getAllNotifications(@PathVariable UUID notificationId, @CurrentUser UserPrincipal currentUser){
        return notificationService.getOneUserNotification(notificationId, currentUser.getId());
    }


}
