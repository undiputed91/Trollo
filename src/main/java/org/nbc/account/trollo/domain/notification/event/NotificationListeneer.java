package org.nbc.account.trollo.domain.notification.event;

import lombok.RequiredArgsConstructor;
import org.nbc.account.trollo.domain.notification.entity.Notification;
import org.nbc.account.trollo.domain.notification.repository.NotificationRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NotificationListeneer {

    private final NotificationRepository notificationRepository;

    @EventListener
    public void recordNotification(CardEvent event) {
        Notification notification = new Notification(event);
        notificationRepository.save(notification);
    }

}
