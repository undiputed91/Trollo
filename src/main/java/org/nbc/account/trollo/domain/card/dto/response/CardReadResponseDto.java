package org.nbc.account.trollo.domain.card.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CardReadResponseDto(
    Long id,
    String title,
    String content,
    String color,
    LocalDateTime deadline
) {

}
