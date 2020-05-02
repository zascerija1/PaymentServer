package ba.unsa.etf.si.payment.repository;

import ba.unsa.etf.si.payment.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findAllByApplicationUser_Id(Long applicationId);
    Notification findByIdAndApplicationUser_Id(UUID notificationId, Long applicationUserId);
}
