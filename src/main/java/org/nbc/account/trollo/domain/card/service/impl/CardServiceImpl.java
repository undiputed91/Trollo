package org.nbc.account.trollo.domain.card.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.nbc.account.trollo.domain.board.entity.Board;
import org.nbc.account.trollo.domain.board.exception.NotFoundBoardException;
import org.nbc.account.trollo.domain.board.repository.BoardRepository;
import org.nbc.account.trollo.domain.card.dto.request.CardCreateRequestDto;
import org.nbc.account.trollo.domain.card.dto.request.CardUpdateRequestDto;
import org.nbc.account.trollo.domain.card.dto.response.CardAllReadResponseDto;
import org.nbc.account.trollo.domain.card.dto.response.CardReadResponseDto;
import org.nbc.account.trollo.domain.card.entity.Card;
import org.nbc.account.trollo.domain.card.entity.Card.CardBuilder;
import org.nbc.account.trollo.domain.card.exception.NotFoundCardException;
import org.nbc.account.trollo.domain.card.mapper.CardMapper;
import org.nbc.account.trollo.domain.card.repository.CardRepository;
import org.nbc.account.trollo.domain.card.service.CardService;
import org.nbc.account.trollo.domain.checklist.dto.response.CheckListResponseDto;
import org.nbc.account.trollo.domain.checklist.entity.CheckList;
import org.nbc.account.trollo.domain.checklist.exception.NotFoundCheckListException;
import org.nbc.account.trollo.domain.checklist.repository.CheckListRepository;
import org.nbc.account.trollo.domain.section.entity.Section;
import org.nbc.account.trollo.domain.section.exception.NotFoundSectionException;
import org.nbc.account.trollo.domain.section.exception.NotFoundSectionInBoardException;
import org.nbc.account.trollo.domain.section.repository.ColumnRepository;
import org.nbc.account.trollo.domain.user.entity.User;
import org.nbc.account.trollo.domain.userboard.entity.UserBoard;
import org.nbc.account.trollo.domain.userboard.entity.UserBoardRole;
import org.nbc.account.trollo.domain.userboard.exception.ForbiddenAccessBoardException;
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
    private final CheckListRepository checkListRepository;

    @Override
    @Transactional
    public void createCard(final CardCreateRequestDto cardCreateRequestDto, final Long boardId,
        final Long sectionId, final User user) {
        // 보드를 찾는다.
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new NotFoundBoardException(ErrorCode.NOT_FOUND_BOARD));

        // 해당 보드에 사용자가 포함되어 있는지 확인한다.
        checkUserInBoard(boardId, user.getId());

        // 해당 보드에 색션이 있는지 찾는다.
        Section section = columnRepository.findById(sectionId)
            .orElseThrow(() -> new NotFoundSectionException(ErrorCode.NOT_FOUND_SECTION));
        if (!section.getBoard().getId().equals(board.getId())) {
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
        if (lastCardOptional.isEmpty()) {
            cardRepository.save(cardBuilder.build());
            return;
        }

        // 해당 색션에 카드가 있다면, 맨 마지막 카드의 상태를 변경하고 새로운 카드를 맨 마지막에 추가한다.
        Card lastCard = lastCardOptional.get();

        Card createdCard = cardBuilder.prevCard(lastCard).build();
        createdCard = cardRepository.save(createdCard);

        lastCard.setNextCard(createdCard);
    }

    @Override
    @Transactional(readOnly = true)
    public CardReadResponseDto getCard(final Long cardId, final User user) {
        // 해당 카드가 있는 보드에 사용자가 속하는지 확인한다.
        Card card = cardRepository.findById(cardId)
            .orElseThrow(() -> new NotFoundCardException(ErrorCode.NOT_FOUND_CARD));

        Board board = card.getSection().getBoard();
        checkUserInBoard(board.getId(), user.getId());

        List<CheckListResponseDto> checkListResponseDtos = getCheckLists(cardId);

        return CardMapper.INSTANCE.toCardReadResponseDto(card, checkListResponseDtos);
    }

    @Override
    public List<CardAllReadResponseDto> getCardAllByBoard(final Long boardId, final User user) {
        // 해당 보드에 사용자가 속하는지 확인한다.
        checkUserInBoard(boardId, user.getId());

        List<Card> cards = cardRepository.findAllBySection_Board_Id(boardId);
        return CardMapper.INSTANCE.toCardAllReadResponseDtoList(cards);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CardAllReadResponseDto> getCardAllBySection(final Long sectionId, final User user) {
        // 색션이 속한 보드에 사용자가 속하는지 확인한다.
        Section section = columnRepository.findById(sectionId)
            .orElseThrow(() -> new NotFoundSectionException(ErrorCode.NOT_FOUND_SECTION));
        checkUserInBoard(section.getBoard().getId(), user.getId());

        List<Card> cards = cardRepository.findAllBySectionId(sectionId);
        return CardMapper.INSTANCE.toCardAllReadResponseDtoList(cards);
    }

    @Override
    @Transactional
    public void updateCard(final Long cardId, final CardUpdateRequestDto cardUpdateRequestDto,
        final User user) {
        Card card = cardRepository.findById(cardId)
            .orElseThrow(() -> new NotFoundCardException(ErrorCode.NOT_FOUND_CARD));

        // 카드가 있는 보드에 사용자가 속하는지 확인한다.
        Long boardId = card.getSection().getBoard().getId();
        checkUserInBoard(boardId, user.getId());

        card.update(
            cardUpdateRequestDto.title(),
            cardUpdateRequestDto.content(),
            cardUpdateRequestDto.color(),
            cardUpdateRequestDto.deadline()
        );
    }

    @Override
    @Transactional
    public void deleteCard(final Long cardId, final User user) {
        Card card = cardRepository.findById(cardId)
            .orElseThrow(() -> new NotFoundCardException(ErrorCode.NOT_FOUND_CARD));

        // 카드가 있는 보드에 사용자가 속하는지 확인한다.
        Long boardId = card.getSection().getBoard().getId();
        checkUserInBoard(boardId, user.getId());

        // 카드 삭제 시, 이전 카드와 다음 카드의 순서를 재설정한다.
        Card prevCard = card.getPrevCard();
        Card nextCard = card.getNextCard();
        card.getPrevCard().setNextCard(nextCard);
        card.getNextCard().setPrevCard(prevCard);

        cardRepository.delete(card);
    }

    private void checkUserInBoard(Long boardId, Long userId) {
        UserBoard userBoard = userBoardRepository.findByBoardIdAndUserId(boardId, userId)
            .orElseThrow(() -> new NotFoundUserBoardException(ErrorCode.NOT_FOUND_USER_BOARD));
        if (userBoard.getRole() == UserBoardRole.WAITING) {
            throw new ForbiddenAccessBoardException(ErrorCode.FORBIDDEN_ACCESS_BOARD);
        }
    }

    private List<CheckListResponseDto> getCheckLists(Long cardId) {
        List<CheckList> checkLists = checkListRepository.findByCardId(cardId)
            .orElseThrow(() -> new NotFoundCheckListException(ErrorCode.NOT_FOUND_CHECKLIST));

        List<CheckList> checklistsWithTrue = checkListRepository.findByCardIdAndCheckSignIsTrue(
                cardId)
            .orElseThrow(() -> new NotFoundCheckListException(ErrorCode.NOT_FOUND_CHECKLIST));
        double rate = (double) checklistsWithTrue.size() / checkLists.size();

        List<CheckListResponseDto> checkListResponseDtos = new ArrayList<>();
        for (CheckList checkList : checkLists) {
            CheckListResponseDto checkListResponseDto = CheckListResponseDto.builder()
                .description(checkList.getDescription())
                .checkSign(checkList.isCheckSign())
                .rate(rate)
                .build();
            checkListResponseDtos.add(checkListResponseDto);
        }
        return checkListResponseDtos;
    }

}
