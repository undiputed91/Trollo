package org.nbc.account.trollo.comment.dto.res;

import jakarta.validation.constraints.Size;

public record CommentSaveRes(
    @Size(max = 500)
    String content
) {

}
