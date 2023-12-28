package org.nbc.account.trollo.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Getter
@RequiredArgsConstructor
@Validated
public class SignupReq {

  //@Size(min = 5,max = 25, message ="username should be longer than 3 and shorter than 15")
  //@Pattern(regexp = "(^[a-zA-Z_0-9]*$)@(^[a-zA-Z_0-9]*$).(^[a-z]*$)", message = "only email@domain.com is allowed")
  private String email;

  private String nickname;

  @Size(min = 4,max = 15, message ="pw should be longer than 4 and shorter than 15")
  @Pattern(regexp = "^[a-zA-Z_0-9]*$", message = "only alphabets and numbers are allowed for pw")
  private String password;

  private String passwordCheck;

}
