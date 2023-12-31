package org.nbc.account.trollo.domain.usernotification.repository;

import org.nbc.account.trollo.domain.usernotification.entity.UserNotification;
import org.nbc.account.trollo.domain.usernotification.entity.UserNotificationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNotificationRepository extends JpaRepository<UserNotification, UserNotificationId> {

}
