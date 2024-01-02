package org.nbc.account.trollo.domain.section.service.impl;

import lombok.RequiredArgsConstructor;
import org.nbc.account.trollo.domain.board.entity.Board;
import org.nbc.account.trollo.domain.board.repository.BoardRepository;
import org.nbc.account.trollo.domain.card.exception.NotFoundCardException;
import org.nbc.account.trollo.domain.section.dto.SectionCreateRequestDto;
import org.nbc.account.trollo.domain.section.entity.Section;
import org.nbc.account.trollo.domain.section.exception.NotFoundSectionException;
import org.nbc.account.trollo.domain.section.repository.SectionRepository;
import org.nbc.account.trollo.domain.section.service.SectionService;
import org.nbc.account.trollo.domain.user.entity.User;
import org.nbc.account.trollo.domain.userboard.exception.NotFoundUserBoardException;
import org.nbc.account.trollo.domain.userboard.repository.UserBoardRepository;
import org.nbc.account.trollo.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;
    private final UserBoardRepository userBoardRepository;
    private final BoardRepository boardRepository;

    @Override
    public void createSection(Long boardId, SectionCreateRequestDto sectionCreateRequestDto,
        User user) {
        checkUserInBoard(boardId, user.getId());
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new NotFoundSectionException(ErrorCode.NOT_FOUND_SECTION));
        Section sectionBuilder = Section.builder()
            .board(board)
            .name(sectionCreateRequestDto.name())
            .build();
        sectionRepository.save(sectionBuilder);
    }

    @Override
    @Transactional
    public void updateSection(Long sectionId, SectionCreateRequestDto sectionCreateRequestDto,
        User user) {
        Section section = sectionRepository.findById(sectionId)
            .orElseThrow(() -> new NotFoundCardException(ErrorCode.NOT_FOUND_CARD));
        Long boardId = section.getBoard().getId();
        checkUserInBoard(boardId, user.getId());

        section.update(
            sectionCreateRequestDto.name()
        );

    }

    @Override
    @Transactional
    public void deleteSection(Long sectionId, User user) {
        Section section = sectionRepository.findById(sectionId)
            .orElseThrow(() -> new NotFoundCardException(ErrorCode.NOT_FOUND_CARD));
        Long boardId = section.getBoard().getId();
        checkUserInBoard(boardId, user.getId());

//        Section prevSection = section.getPrevSection();
//        Section nextSection = section.getNextSection();
//        section.getPrevSection().setNextSection(nextSection);
//        section.getNextSection().setPrevSection(prevSection);

        sectionRepository.delete(section);
    }

    private void checkUserInBoard(Long boardId, Long userId) {
        userBoardRepository.findByBoardIdAndUserId(boardId, userId)
            .orElseThrow(() -> new NotFoundUserBoardException(ErrorCode.NOT_FOUND_USER_BOARD));

    }
}

