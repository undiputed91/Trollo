package org.nbc.account.trollo.domain.card.controller;

import lombok.RequiredArgsConstructor;
import org.nbc.account.trollo.domain.card.dto.request.CardCreateRequestDto;
import org.nbc.account.trollo.domain.card.service.CardService;
import org.nbc.account.trollo.global.dto.ApiResponse;
import org.nbc.account.trollo.global.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CardController {

    private final CardService cardService;

    @PostMapping("/boards/{boardId}/columns/{columnId}/cards")
    public ApiResponse<Void> createCard(
        @PathVariable Long boardId,
        @PathVariable Long columnId,
        @RequestBody CardCreateRequestDto cardCreateRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        cardService.createCard(cardCreateRequestDto, boardId, columnId, userDetails.getUser());
        return new ApiResponse<>(HttpStatus.CREATED.value(), "카드 생성", null);
    }
}
