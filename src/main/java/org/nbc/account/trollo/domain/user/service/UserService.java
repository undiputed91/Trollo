package org.nbc.account.trollo.domain.user.service;

import org.nbc.account.trollo.domain.user.dto.request.LoginReq;
import org.nbc.account.trollo.domain.user.dto.request.SignupReq;

public interface UserService {

  public void signup(SignupReq signupReq);
  public void login(LoginReq loginReq);

}
