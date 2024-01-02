package org.nbc.account.trollo.domain.card.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import org.nbc.account.trollo.domain.checklist.dto.request.CheckListRequestDto;
import org.nbc.account.trollo.domain.comment.dto.res.CommentReadResponseDto;

@Builder
public record CardReadDetailResponseDto(
    Long id,
    String title,
    String content,
    String color,
    LocalDateTime deadline,
    List<CheckListRequestDto> checkList,
    Float rate,
    List<CommentReadResponseDto> comments
) {

}
