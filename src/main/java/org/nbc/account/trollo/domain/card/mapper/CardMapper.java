package org.nbc.account.trollo.domain.card.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.nbc.account.trollo.domain.card.dto.response.CardReadResponseDto;
import org.nbc.account.trollo.domain.card.entity.Card;

@Mapper
public interface CardMapper {

    CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);

    CardReadResponseDto toCardReadResponseDto(Card card);
}
