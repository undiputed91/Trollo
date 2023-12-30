package org.nbc.account.trollo.domain.invitation.service.impl;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.nbc.account.trollo.domain.board.entity.Board;
import org.nbc.account.trollo.domain.board.repository.BoardRepository;
import org.nbc.account.trollo.domain.invitation.dto.response.InvitationRes;
import org.nbc.account.trollo.domain.invitation.dto.response.UserBoardRes;
import org.nbc.account.trollo.domain.invitation.exception.InvitationDomainException;
import org.nbc.account.trollo.domain.invitation.service.InvitationService;
import org.nbc.account.trollo.domain.user.entity.User;
import org.nbc.account.trollo.domain.user.repository.UserRepository;
import org.nbc.account.trollo.domain.userboard.entity.UserBoard;
import org.nbc.account.trollo.domain.userboard.entity.UserBoardRole;
import org.nbc.account.trollo.domain.userboard.repository.UserBoardRepository;
import org.nbc.account.trollo.global.exception.ErrorCode;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InvitationServiceImpl implements InvitationService {

  private final UserBoardRepository userBoardRepository;
  private final UserRepository userRepository;
  private final BoardRepository boardRepository;

  @Override
  public void createInvitation(Long boardId, Long userId, User user) {

    Board board = getBoardById(boardId);
    checkIfParticipants(user, board);

    //check if the person is inviting oneself
    User guest = getGuestById(userId, user);

    //check if userboard doesn't exists with the boardId and userId
    if (ifUserBoardExists(boardId, userId)) {
      throw new InvitationDomainException(ErrorCode.ALREADY_EXIST_INVITATION);
    }

    UserBoard invitation = UserBoard.builder()
        .user(guest)
        .board(board)
        .role(UserBoardRole.WAITING)
        .build();
    userBoardRepository.save(invitation);

  }

  @Override
  public List<InvitationRes> getInvitations(User user) {

    List<UserBoard> userBoardList = userBoardRepository.findAllByUserAndRoleEquals(user,
        UserBoardRole.WAITING).orElseThrow(() ->
        new InvitationDomainException(ErrorCode.NOT_FOUND_INVITATION)
    );

    List<InvitationRes> invitations = userBoardList.stream()
        .map((UserBoard userboard) -> new InvitationRes(userboard.getBoard().getId())).toList();

    return invitations;
  }

  @Transactional
  @Override
  public UserBoardRes approveInvitation(Long boardId, User user) {

    Board board = getBoardById(boardId);

    UserBoard userBoard = getInvitation(user, board);

    //delete existing User and Board relation (invitation) to not use @Setter in UserBoard Entity
    userBoardRepository.deleteByUserAndBoard(user, board);
    //save new User and Board relation which means the person is a participant of the board
    UserBoard newUserBoard = new UserBoard(user, board, UserBoardRole.PARTICIPANT);
    userBoardRepository.save(newUserBoard);

    return new UserBoardRes(newUserBoard.getUser().getEmail(), newUserBoard.getBoard().getId(),
        newUserBoard.getRole());

  }



  private User getGuestById(Long userId, User user) {

    User guest = userRepository.findById(userId).orElseThrow(() ->
        new InvitationDomainException(ErrorCode.NOT_FOUND_USER));

    //can't invite oneself
    if (guest.getId().equals(user.getId())) {
      throw new InvitationDomainException(ErrorCode.SELF_CANNOT_BE_INVITED);
    }

    return guest;

  }

  private Board getBoardById(Long boardId) {
    Board board = boardRepository.findById(boardId).orElseThrow(() ->
        new InvitationDomainException(ErrorCode.NOT_FOUND_BOARD));

    return board;
  }

  private boolean ifUserBoardExists(Long boardId, Long userId) {

    return userBoardRepository.existsByBoardIdAndUserId(boardId, userId);

  }

  private void checkIfParticipants(User user, Board board) {
    UserBoard userBoard = userBoardRepository.findUserBoardByUserAndBoard(user, board)
        .orElseThrow(() ->
            new InvitationDomainException(ErrorCode.ONLY_PARTICIPANTS_CAN_INVITE));
    //if the user is not participating but just waiting in line
    if (userBoard.getRole().equals(UserBoardRole.WAITING)) {
      throw new InvitationDomainException(ErrorCode.ONLY_PARTICIPANTS_CAN_INVITE);
    }
  }

  private UserBoard getInvitation(User user, Board board) {
    UserBoard userBoard = userBoardRepository.findUserBoardByUserAndBoard(user, board)
        .orElseThrow(() -> new InvitationDomainException(ErrorCode.NOT_FOUND_INVITATION));

    //if the user is not waiting but a participant or a creator of the board
    if (!userBoard.getRole().equals(UserBoardRole.WAITING)) {
      throw new InvitationDomainException(ErrorCode.NOT_FOUND_INVITATION);
    }

    return userBoard;
  }

}