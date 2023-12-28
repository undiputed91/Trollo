package org.nbc.account.trollo.domain.card.dto.response;

import lombok.Builder;

@Builder
public record CardAllReadResponseDto(
    Long id,
    String title,
    String color
) {

}
