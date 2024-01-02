package org.nbc.account.trollo.domain.checklist.service;

import org.nbc.account.trollo.domain.checklist.dto.request.CheckListCreateRequestDto;
import org.nbc.account.trollo.domain.checklist.dto.request.CheckListRequestDto;
import org.nbc.account.trollo.global.security.UserDetailsImpl;

public interface CheckListService {

    void createList(Long cardId, CheckListCreateRequestDto requestDto, UserDetailsImpl userDetails);

    void updateCheckList(Long cardId, Long checkListId, CheckListRequestDto requestDto,
        UserDetailsImpl userDetails);

    void deleteCheckList(Long cardId, Long checkListId, UserDetailsImpl userDetails);
}
