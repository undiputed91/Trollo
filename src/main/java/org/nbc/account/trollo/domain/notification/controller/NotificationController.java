package org.nbc.account.trollo.domain.notification.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.nbc.account.trollo.domain.notification.dto.response.NotificationResponseDto;
import org.nbc.account.trollo.domain.notification.service.NotifiactionService;
import org.nbc.account.trollo.global.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NotificationController {

    private final NotifiactionService notifiactionService;

    @GetMapping("api/v1/notifications")
    public ApiResponse<List<NotificationResponseDto>> getNotifications() {
        List<NotificationResponseDto>responseDtos =  notifiactionService.getNotifications();
        return new ApiResponse<>(HttpStatus.OK.value(), "알림 조회", responseDtos);
    }
}
