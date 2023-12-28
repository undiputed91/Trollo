package org.nbc.account.trollo.domain.card.service;

import org.nbc.account.trollo.domain.card.dto.request.CardCreateRequestDto;
import org.nbc.account.trollo.domain.user.entity.User;

public interface CardService {

    void createCard(final CardCreateRequestDto cardCreateRequestDto, Long boardId, Long columnId, User user);
}
