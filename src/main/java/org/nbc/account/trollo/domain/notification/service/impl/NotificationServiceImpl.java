package org.nbc.account.trollo.domain.notification.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.nbc.account.trollo.domain.notification.dto.response.NotificationResponseDto;
import org.nbc.account.trollo.domain.notification.entity.Notification;
import org.nbc.account.trollo.domain.notification.entity.NotificationType;
import org.nbc.account.trollo.domain.notification.repository.NotificationRepository;
import org.nbc.account.trollo.domain.notification.service.NotifiactionService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotifiactionService {

    private final NotificationRepository notificationRepository;

    @Override
    public List<NotificationResponseDto> getNotifications() {
        List<Notification> notificationList = notificationRepository.findAll();
        List<NotificationResponseDto> notificationResponseDtos = new ArrayList<>();

        for (Notification notification : notificationList) {
            NotificationType notificationEnum = notification.getFieldName();
            String message = notification.getFieldContent();
            LocalDateTime createdAt = notification.getCreatedAt();
            notificationResponseDtos.add(
                new NotificationResponseDto(notificationEnum, message, createdAt));
        }
        return notificationResponseDtos;
    }
}
