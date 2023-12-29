package org.nbc.account.trollo.domain.user.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public record LoginReq (String email,String password){

}
