package org.nbc.account.trollo.domain.invitation.controller;

import lombok.RequiredArgsConstructor;
import org.nbc.account.trollo.domain.invitation.service.InvitationService;
import org.nbc.account.trollo.global.dto.ApiResponse;
import org.nbc.account.trollo.global.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class InvitationController {

  private final InvitationService invitationService;

  // invite user to a board
  @PostMapping("/boards/{boardId}/invite/{userId}")
  public ApiResponse<Void> invite(
      @PathVariable(name = "boardId") Long boardId,
      @PathVariable(name = "userId") Long userId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    invitationService.createInvitation(boardId, userId, userDetails.getUser());
    return new ApiResponse<>(HttpStatus.CREATED.value(), "successfully invited user");
  }
}
