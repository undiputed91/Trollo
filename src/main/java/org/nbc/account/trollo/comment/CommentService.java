package org.nbc.account.trollo.comment;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.nbc.account.trollo.comment.dto.req.CommentSaveReq;
import org.nbc.account.trollo.comment.dto.res.CommentSaveRes;
import org.nbc.account.trollo.comment.entity.CommentEntity;
import org.nbc.account.trollo.comment.entity.CommentRepository;
import org.nbc.account.trollo.s3.S3Provider;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentSaveRes saveComment(CommentSaveReq req) {
        CommentEntity commentEntity = new CommentEntity(req.content());
        return CommentServiceMapper.INSTANCE.toCommentSaveRes(
            commentRepository.save(commentEntity));
    }

    @Mapper
    public interface CommentServiceMapper {

        CommentService.CommentServiceMapper INSTANCE = Mappers.getMapper(
            CommentService.CommentServiceMapper.class);

        CommentSaveRes toCommentSaveRes(CommentEntity commentEntity);
    }

}
