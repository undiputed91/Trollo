package org.nbc.account.trollo.domain.user.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginReq {

  private String email;
  private String password;

}
