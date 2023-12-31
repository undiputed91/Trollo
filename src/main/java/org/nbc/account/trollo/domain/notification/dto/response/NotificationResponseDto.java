package org.nbc.account.trollo.domain.notification.dto.response;

import java.time.LocalDateTime;
import org.nbc.account.trollo.domain.notification.entity.NotificationEnum;

public record NotificationResponseDto(
    NotificationEnum fieldName,
    String fieldContent,
    LocalDateTime createdAt
) {

}
