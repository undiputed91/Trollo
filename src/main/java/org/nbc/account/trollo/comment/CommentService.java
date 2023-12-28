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
    private final S3Provider s3Provider;
    private final CommentRepository commentRepository;

    public CommentSaveRes saveComment(CommentSaveReq req, MultipartFile multipartFile)
        throws IOException {
        String imageUrl = s3Provider.saveFile(multipartFile, "comment");
        CommentEntity comment = CommentEntity.builder()
            .content(req.getContent())
            .imageUrl(imageUrl)
            .build();
        CommentEntity newCommentEntity = commentRepository.save(comment);
        return CommentServiceMapper.INSTANCE.toCommentSaveRes(
            commentRepository.save(CommentEntity.builder()
                .content(req.getContent())
                .imageUrl(imageUrl)
                .build()));
    }


    @Mapper
    public interface CommentServiceMapper{
        CommentService.CommentServiceMapper INSTANCE = Mappers.getMapper(
            CommentService.CommentServiceMapper.class);

        CommentSaveRes toCommentSaveRes(CommentEntity commentEntity);
    }

}
