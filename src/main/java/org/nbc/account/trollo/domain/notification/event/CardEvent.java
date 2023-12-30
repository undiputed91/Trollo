package org.nbc.account.trollo.domain.notification.event;

import lombok.Getter;
import org.nbc.account.trollo.domain.card.entity.Card;
import org.nbc.account.trollo.domain.notification.entity.NotificationEnum;

@Getter
public class CardEvent {

    private Card card;

    private NotificationEnum notificationEnum;

    public CardEvent(Card card, NotificationEnum notificationEnum) {
        this.card = card;
        this.notificationEnum = notificationEnum;
    }

}
