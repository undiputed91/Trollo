package org.nbc.account.trollo.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.nbc.account.trollo.domain.user.dto.request.LoginReq;
import org.nbc.account.trollo.domain.user.dto.request.SignupReq;
import org.nbc.account.trollo.domain.user.entity.User;
import org.nbc.account.trollo.domain.user.repository.UserRepository;
import org.nbc.account.trollo.domain.user.service.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public void signup(SignupReq signupReq) {
    String email = signupReq.getEmail();
    String nickname = signupReq.getNickname();
    String password = passwordEncoder.encode(signupReq.getPassword());
    String passwordCheck = signupReq.getPasswordCheck();

    // check username duplication
    if (userRepository.findByEmail(email).isPresent()) {
      throw new IllegalArgumentException("중복된 이메일입니다.");
    }

    // check password
    if (!passwordEncoder.matches(passwordCheck, password)) {
      throw new IllegalArgumentException("password check가 password와 일치하지 않습니다.");
    }

    //register user
    User user = User.builder()
        .email(email)
        .password(password)
        .nickname(nickname)
        .build();

    userRepository.save(user);
  }

}
