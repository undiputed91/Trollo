package org.nbc.account.trollo.domain.card.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.nbc.account.trollo.domain.board.entity.Board;
import org.nbc.account.trollo.domain.board.exception.NotFoundBoardException;
import org.nbc.account.trollo.domain.board.repository.BoardRepository;
import org.nbc.account.trollo.domain.card.dto.request.CardCreateRequestDto;
import org.nbc.account.trollo.domain.card.dto.response.CardAllReadResponseDto;
import org.nbc.account.trollo.domain.card.dto.response.CardReadResponseDto;
import org.nbc.account.trollo.domain.card.entity.Card;
import org.nbc.account.trollo.domain.card.exception.ForbiddenAccessCardException;
import org.nbc.account.trollo.domain.card.exception.NotFoundCardException;
import org.nbc.account.trollo.domain.card.mapper.CardMapper;
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
    public void createCard(final CardCreateRequestDto cardCreateRequestDto, final Long boardId,
        final Long columnId, final User user) {
        // 보드를 찾는다.
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new NotFoundBoardException(ErrorCode.NOT_FOUND_BOARD));

        // 해당 보드에 사용자가 포함되어 있는지 확인한다.
        if (userBoardRepository.existsByBoardIdAndUserId(board.getId(), user.getId())) {
            throw new NotFoundUserBoardException(ErrorCode.NOT_FOUND_USER_BOARD);
        }

        // 해당 보드에 칼럼이 있는지 찾는다.
        Section section = columnRepository.findById(columnId)
            .orElseThrow(() -> new NotFoundSectionException(ErrorCode.NOT_FOUND_SECTION));
        if (!section.getBoard().getId().equals(board.getId())) {
            throw new NotFoundSectionInBoardException(ErrorCode.NOT_FOUND_SECTION_IN_BOARD);
        }

        Card createdCard = Card.builder()
            .title(cardCreateRequestDto.title())
            .section(section)
            .build();

        cardRepository.save(createdCard);
    }

    @Override
    @Transactional(readOnly = true)
    public CardReadResponseDto getCard(final Long cardId, final User user) {
        // 해당 카드가 있는 보드에 사용자가 속하는지 확인한다.
        Card card = cardRepository.findById(cardId)
            .orElseThrow(() -> new NotFoundCardException(ErrorCode.NOT_FOUND_CARD));

        Board board = card.getSection().getBoard();
        if (!userBoardRepository.existsByBoardIdAndUserId(board.getId(), user.getId())) {
            throw new ForbiddenAccessCardException(ErrorCode.FORBIDDEN_ACCESS_CARD);
        }

        return CardMapper.INSTANCE.toCardReadResponseDto(card);
    }

    @Override
    public List<CardAllReadResponseDto> getCardAllByBoard(final Long boardId, final User user) {
        // 해당 보드에 사용자가 속하는지 확인한다.
        if(!userBoardRepository.existsByBoardIdAndUserId(boardId, user.getId())){
            throw new ForbiddenAccessCardException(ErrorCode.FORBIDDEN_ACCESS_CARD);
        }

        List<Card> cards = cardRepository.findAllBySection_Board_Id(boardId);
        return CardMapper.INSTANCE.toCardAllReadResponseDtoList(cards);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CardAllReadResponseDto> getCardAllBySection(final Long sectionId, final User user) {
        // 색션이 속한 보드에 사용자가 속하는지 확인한다.
        Section section = columnRepository.findById(sectionId)
            .orElseThrow(() -> new NotFoundSectionException(ErrorCode.NOT_FOUND_SECTION));
        if(!userBoardRepository.existsByBoardIdAndUserId(section.getBoard().getId(), user.getId())){
            throw new ForbiddenAccessCardException(ErrorCode.FORBIDDEN_ACCESS_CARD);
        }

        List<Card> cards = cardRepository.findAllBySectionId(sectionId);
        return CardMapper.INSTANCE.toCardAllReadResponseDtoList(cards);
    }

}
