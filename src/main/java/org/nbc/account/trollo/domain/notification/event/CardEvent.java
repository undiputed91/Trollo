package org.nbc.account.trollo.domain.notification.event;

import org.nbc.account.trollo.domain.card.entity.Card;
import org.nbc.account.trollo.domain.notification.entity.NotificationType;


public record CardEvent(
    Card card,
    NotificationType notificationEnum) {

}
