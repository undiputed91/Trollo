package org.nbc.account.trollo.domain.notification.service;

import java.util.List;
import org.nbc.account.trollo.domain.notification.dto.response.NotificationResponseDto;

public interface NotifiactionService {

    List<NotificationResponseDto> getNotifications();

}
