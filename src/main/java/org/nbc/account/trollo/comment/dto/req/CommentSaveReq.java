package org.nbc.account.trollo.comment.dto.req;


import jakarta.validation.constraints.Size;

public record CommentSaveReq(
    @Size(max = 500)
    String content

) {

}
