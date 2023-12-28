package org.nbc.account.trollo.comment;


import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.nbc.account.trollo.comment.dto.req.CommentSaveReq;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("api/v1/cards/comments")
@RestController

public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public void saveComment(@RequestPart CommentSaveReq req) {
        commentService.saveComment(req);
    }

}
