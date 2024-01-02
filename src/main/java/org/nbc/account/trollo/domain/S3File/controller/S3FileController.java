package org.nbc.account.trollo.domain.S3File.controller;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.nbc.account.trollo.domain.S3File.service.S3FileService;
import org.nbc.account.trollo.global.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class S3FileController {

    private final S3FileService s3FileService;

    @PostMapping("/api/{cardId}") // 마지막에 다 수정 예정
    public ApiResponse<Void> saveFile(@PathVariable Long cardId, MultipartFile multipartFile)
        throws IOException {
        s3FileService.saveFile(cardId, multipartFile);
        return new ApiResponse<>(HttpStatus.OK.value(), "save_File");
    }
}
