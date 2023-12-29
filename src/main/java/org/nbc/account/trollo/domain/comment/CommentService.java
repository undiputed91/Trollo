package org.nbc.account.trollo.domain.comment;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.nbc.account.trollo.domain.comment.dto.req.CommentDeleteReq;
import org.nbc.account.trollo.domain.comment.dto.req.CommentSaveReq;
import org.nbc.account.trollo.domain.comment.dto.res.CommentSaveRes;
import org.nbc.account.trollo.domain.comment.dto.res.CommentUpdateRes;
import org.nbc.account.trollo.domain.comment.entity.CommentEntity;
import org.nbc.account.trollo.domain.comment.entity.CommentRepository;
import org.nbc.account.trollo.domain.user.entity.User;
import org.nbc.account.trollo.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;


    public CommentSaveRes saveComment(CommentSaveReq req) {
        User nickname = findNickname(req.nickname());
        CommentEntity commentEntity = new CommentEntity(req.content(), nickname);
        return CommentServiceMapper.INSTANCE.toCommentSaveRes(
            commentRepository.save(commentEntity));
    }

    private User findNickname(String nickname) {
        User userNickname = userRepository.findByNickname(nickname);
        if (userNickname == null) {
            throw new RuntimeException();
        }
        return userNickname;
    }

    @Transactional
    public void deleteComment(CommentDeleteReq req) {
        CommentEntity commentEntity = commentRepository.findByCommentId(req.commentId());
        if (commentEntity == null) {
            throw new IllegalArgumentException("Not_Found_Entity");
        }
        commentRepository.delete(commentEntity);
    }

    @Mapper
    public interface CommentServiceMapper {

        CommentService.CommentServiceMapper INSTANCE = Mappers.getMapper(
            CommentService.CommentServiceMapper.class);

        @Mapping(source = "user.nickname", target = "nickname")
        CommentSaveRes toCommentSaveRes(CommentEntity commentEntity);

        CommentUpdateRes toCommentUpdateRes(CommentEntity commentEntity);
    }

}
