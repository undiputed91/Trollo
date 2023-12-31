package org.nbc.account.trollo.domain.usernotification.repository;

import java.util.Optional;
import org.nbc.account.trollo.domain.usernotification.entity.UserNotification;
import org.nbc.account.trollo.domain.usernotification.entity.UserNotificationId;
import org.nbc.account.trollo.domain.usernotification.entity.UserNotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNotificationRepository extends JpaRepository<UserNotification, UserNotificationId> {

    Optional<UserNotification> findByStatus(UserNotificationStatus userNotificationStatus);
}
