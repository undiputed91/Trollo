package org.nbc.account.trollo.domain.checklist.service.impl;

import lombok.RequiredArgsConstructor;
import org.nbc.account.trollo.domain.card.entity.Card;
import org.nbc.account.trollo.domain.card.exception.ForbiddenAccessCardException;
import org.nbc.account.trollo.domain.card.exception.NotFoundCardException;
import org.nbc.account.trollo.domain.card.repository.CardRepository;
import org.nbc.account.trollo.domain.checklist.dto.request.CheckListRequestDto;
import org.nbc.account.trollo.domain.checklist.entity.CheckList;
import org.nbc.account.trollo.domain.checklist.repository.CheckListRepository;
import org.nbc.account.trollo.domain.checklist.service.CheckListService;
import org.nbc.account.trollo.domain.user.entity.User;
import org.nbc.account.trollo.domain.userboard.repository.UserBoardRepository;
import org.nbc.account.trollo.global.exception.ErrorCode;
import org.nbc.account.trollo.global.security.UserDetailsImpl;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CheckListServiceImpl implements CheckListService {

    private final CardRepository cardRepository;
    private final CheckListRepository checkListRepository;
    private final UserBoardRepository userBoardRepository;

    @Override
    public void createList(Long cardId, CheckListRequestDto requestDto,
        UserDetailsImpl userDetails) {
        String description = requestDto.description();
        boolean checkSign = requestDto.checkSign();
        User loginUser = userDetails.getUser();

        Card card = cardRepository.findById(cardId)
            .orElseThrow(() -> new NotFoundCardException(ErrorCode.NOT_FOUND_CARD));

        Long boardId = card.getSection().getBoard().getId();

        if (!userBoardRepository.existsByBoardIdAndUserId(boardId, loginUser.getId())) {
            throw new ForbiddenAccessCardException(ErrorCode.FORBIDDEN_ACCESS_CARD);
        }

        CheckList checkList = CheckList.builder()
            .card(card)
            .description(description)
            .checkSign(checkSign)
            .build();

        checkListRepository.save(checkList);
    }
}
