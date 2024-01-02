package org.nbc.account.trollo.domain.section.dto.response;

import org.nbc.account.trollo.domain.card.dto.response.CardReadResponseDto;

public record SectionReadResponseDto(
    String name,
    CardReadResponseDto cards
) {

}
