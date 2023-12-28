package org.nbc.account.trollo.domain.card.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.nbc.account.trollo.domain.board.entity.Board;
import org.nbc.account.trollo.domain.board.exception.NotFoundBoardException;
import org.nbc.account.trollo.domain.board.repository.BoardRepository;
import org.nbc.account.trollo.domain.card.dto.request.CardCreateRequestDto;
import org.nbc.account.trollo.domain.card.entity.Card;
import org.nbc.account.trollo.domain.card.entity.Card.CardBuilder;
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
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final BoardRepository boardRepository;
    private final UserBoardRepository userBoardRepository;
    private final ColumnRepository columnRepository;

    @Override
    @Transactional
    public void createCard(final CardCreateRequestDto cardCreateRequestDto, final Long boardId,
        final Long sectionId, final User user) {
        // 보드를 찾는다.
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new NotFoundBoardException(ErrorCode.NOT_FOUND_BOARD));

        // 보드에 해당 사용자가 포함되어 있는지 확인한다.
        if (userBoardRepository.existsByBoardAndUser(board, user)) {
            throw new NotFoundUserBoardException(ErrorCode.NOT_FOUND_USER_BOARD);
        }

        // 해당 보드에 색션이 있는지 찾는다.
        Section section = columnRepository.findById(sectionId)
            .orElseThrow(() -> new NotFoundSectionException(ErrorCode.NOT_FOUND_SECTION));
        if (section.getBoard().getId().equals(board.getId())) {
            throw new NotFoundSectionInBoardException(ErrorCode.NOT_FOUND_SECTION_IN_BOARD);
        }

        CardBuilder cardBuilder = Card.builder()
            .title(cardCreateRequestDto.title())
            .prevCard(null)
            .nextCard(null)
            .section(section);

        // 해당 색션에 카드가 없다면, 그냥 추가한다.
        Optional<Card> lastCardOptional = cardRepository.findBySectionIdAndNextCardIsNull(
            section.getId());
        if(lastCardOptional.isEmpty()) {
            cardRepository.save(cardBuilder.build());
            return;
        }

        // 해당 색션에 카드가 있다면, 맨 마지막 카드의 상태를 변경하고 새로운 카드를 맨 마지막에 추가한다.
        Card lastCard = lastCardOptional.get();

        Card createdCard = cardBuilder.prevCard(lastCard).build();
        createdCard = cardRepository.save(createdCard);

        lastCard.setNextCard(createdCard);
    }

}
