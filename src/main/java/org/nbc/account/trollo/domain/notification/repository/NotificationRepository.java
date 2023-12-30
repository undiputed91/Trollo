package org.nbc.account.trollo.domain.notification.repository;

import org.nbc.account.trollo.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

}
