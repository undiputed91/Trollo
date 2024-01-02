package org.nbc.account.trollo.domain.board.dto;

import java.util.List;
import org.nbc.account.trollo.domain.section.dto.response.SectionReadResponseDto;

public record BoardReadResponseDto(
    Long boardId,
    String name,
    List<SectionReadResponseDto> sections
) {

}
