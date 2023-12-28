package org.nbc.account.trollo.domain.card.service.impl;

import lombok.RequiredArgsConstructor;
import org.nbc.account.trollo.domain.board.entity.Board;
import org.nbc.account.trollo.domain.board.exception.NotFoundBoardException;
import org.nbc.account.trollo.domain.board.repository.BoardRepository;
import org.nbc.account.trollo.domain.card.dto.request.CardCreateRequestDto;
import org.nbc.account.trollo.domain.card.entity.Card;
import org.nbc.account.trollo.domain.card.repository.CardRepository;
import org.nbc.account.trollo.domain.card.service.CardService;
import org.nbc.account.trollo.domain.section.entity.Section;
import org.nbc.account.trollo.domain.section.exception.NotFoundSectionException;
import org.nbc.account.trollo.domain.section.exception.NotFoundSectionInBoardException;
import org.nbc.account.trollo.domain.section.repository.ColumnRepository;
import org.nbc.account.trollo.domain.user.entity.User;
import org.nbc.account.trollo.domain.userboard.exception.NotFoundUserBoardException;
import org.nbc.account.trollo.domain.userboard.repository.UserBoardRepository;
import org.nbc.account.trollo.global.exception.ErrorCode;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final BoardRepository boardRepository;
    private final UserBoardRepository userBoardRepository;
    private final ColumnRepository columnRepository;

    @Override
    public void createCard(final CardCreateRequestDto cardCreateRequestDto, final Long boardId,
        final Long columnId, final User user) {
        // 보드를 찾는다.
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new NotFoundBoardException(ErrorCode.NOT_FOUND_BOARD));

        // 보드에 해당 사용자가 포함되어 있는지 확인한다.
        if (userBoardRepository.existsByBoardAndUser(board, user)) {
            throw new NotFoundUserBoardException(ErrorCode.NOT_FOUND_USER_BOARD);
        }

        // 해당 보드에 칼럼이 있는지 찾는다.
        Section section = columnRepository.findById(columnId)
            .orElseThrow(() -> new NotFoundSectionException(ErrorCode.NOT_FOUND_SECTION));
        if (section.getBoard().getId().equals(board.getId())) {
            throw new NotFoundSectionInBoardException(ErrorCode.NOT_FOUND_SECTION_IN_BOARD);
        }

        Card createdCard = Card.builder()
            .title(cardCreateRequestDto.title())
            .section(section)
            .build();

        cardRepository.save(createdCard);
    }

}
