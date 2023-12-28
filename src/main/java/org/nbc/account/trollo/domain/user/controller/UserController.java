package org.nbc.account.trollo.domain.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nbc.account.trollo.domain.user.dto.request.LoginReq;
import org.nbc.account.trollo.domain.user.dto.request.SignupReq;
import org.nbc.account.trollo.domain.user.exception.UserDomainException;
import org.nbc.account.trollo.domain.user.service.UserService;
import org.nbc.account.trollo.global.dto.ApiResponse;
import org.nbc.account.trollo.global.exception.ErrorCode;
import org.nbc.account.trollo.global.jwt.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  // sign up
  @PostMapping("/signup")
  public ApiResponse<Void> signup(
      @Valid @RequestBody SignupReq signupReq,
      BindingResult bindingResult) {

    validate(bindingResult);
    userService.signup(signupReq);
    // if succeeded
    return new ApiResponse<>(HttpStatus.CREATED.value(), "completed signing up");
  }

  // login
  @PostMapping("/login")
  public ApiResponse<Void> login(@RequestBody LoginReq loginReq,
      HttpServletResponse response) {

    userService.login(loginReq,response);
    return new ApiResponse<>(HttpStatus.OK.value(), "login succeeded");
  }
  private void validate(BindingResult bindingResult){
    // Validation exception
    if(bindingResult.hasErrors()){
      throw new UserDomainException(ErrorCode.BAD_FORM);
    };
  }

}
