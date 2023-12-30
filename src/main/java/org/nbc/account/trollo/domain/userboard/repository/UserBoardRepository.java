package org.nbc.account.trollo.domain.userboard.repository;

import java.util.List;
import java.util.Optional;
import org.nbc.account.trollo.domain.board.entity.Board;
import org.nbc.account.trollo.domain.user.entity.User;
import org.nbc.account.trollo.domain.userboard.entity.UserBoard;
import org.nbc.account.trollo.domain.userboard.entity.UserBoardId;
import org.nbc.account.trollo.domain.userboard.entity.UserBoardRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBoardRepository extends JpaRepository<UserBoard, UserBoardId> {

    boolean existsByBoardIdAndUserId(Long boardId, Long userId);

    Optional<UserBoard> findUserBoardByUserAndBoard(User user, Board board);

    Optional<List<UserBoard>> findAllByUserAndRoleEquals(User user, UserBoardRole userBoardRole);

    Optional<UserBoard> findByBoardIdAndUserId(Long id, Long id1);
}
