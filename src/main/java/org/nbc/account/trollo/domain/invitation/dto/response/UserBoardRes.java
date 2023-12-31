package org.nbc.account.trollo.domain.invitation.dto.response;

import org.nbc.account.trollo.domain.userboard.entity.UserBoardRole;

public record UserBoardRes(String email, Long boardId, UserBoardRole role) {

}
