package org.nbc.account.trollo.domain.user.service;

import jakarta.servlet.http.HttpServletResponse;
import org.nbc.account.trollo.domain.user.dto.request.LoginReq;
import org.nbc.account.trollo.domain.user.dto.request.SignupReq;

public interface UserService {

    void signup(SignupReq signupReq);

    void login(LoginReq loginReq, HttpServletResponse response);

}
