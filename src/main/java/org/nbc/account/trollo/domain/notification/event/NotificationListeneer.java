package org.nbc.account.trollo.domain.notification.event;

import lombok.RequiredArgsConstructor;
import org.nbc.account.trollo.domain.notification.entity.Notification;
import org.nbc.account.trollo.domain.notification.repository.NotificationRepository;
import org.nbc.account.trollo.domain.usernotification.entity.UserNotification;
import org.nbc.account.trollo.domain.usernotification.repository.UserNotificationRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NotificationListeneer {

    private final NotificationRepository notificationRepository;
    private final UserNotificationRepository userNotificationRepository;

    @EventListener
    public void recordNotification(CardEvent event) {
        Notification notification = new Notification(event);
        notificationRepository.save(notification);

        UserNotification userNotification = new UserNotification(event,notification);
        userNotificationRepository.save(userNotification);
    }

}
