package org.nbc.account.trollo.domain.invitation.service;

import java.util.List;
import org.nbc.account.trollo.domain.invitation.dto.response.InvitationRes;
import org.nbc.account.trollo.domain.invitation.dto.response.UserBoardRes;
import org.nbc.account.trollo.domain.user.entity.User;

public interface InvitationService {

  void createInvitation(Long boardId, Long userId, User user);

  List<InvitationRes> getInvitations(User user);

  UserBoardRes approveInvitation(Long boardId, User user);

}
