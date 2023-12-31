package org.nbc.account.trollo.domain.invitation.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.nbc.account.trollo.domain.board.entity.Board;
import org.nbc.account.trollo.domain.user.entity.User;
import org.nbc.account.trollo.domain.invitation.entity.InvitationId;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "TB_INVITATION")
//@IdClass(InvitationId.class)
public class Invitation {

//  @Id
//  @ManyToOne
//  @JoinColumn(name = "user_id", nullable = false)
//  private User reciever;
//
//  @Id
//  @ManyToOne
//  @JoinColumn(name = "board_id", nullable = false)
//  private Board board;

  @EmbeddedId
  private InvitationId id;

  @ManyToOne
  @JoinColumn(name = "sender_id", nullable = false)
  private User sender;

  @Builder
  public Invitation(User receiver, User sender, Board board){
    this.id = new InvitationId();
    id.setReceiver(receiver);
    id.setBoard(board);
    this.sender = sender;
  }

}
