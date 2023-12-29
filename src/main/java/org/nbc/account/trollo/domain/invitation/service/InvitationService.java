package org.nbc.account.trollo.domain.invitation.service;

import org.nbc.account.trollo.domain.user.entity.User;

public interface InvitationService {

  public void createInvitation(Long boardId, Long userId, User user);

}
