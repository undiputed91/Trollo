package org.nbc.account.trollo.domain.invitation.dto.response;

import java.util.List;

public record InvitationsRes(List<InvitationRes> receivedInvitations, List<InvitationRes> sentInvitations) {

}
