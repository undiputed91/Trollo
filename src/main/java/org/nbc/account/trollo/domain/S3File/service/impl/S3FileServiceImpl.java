package org.nbc.account.trollo.domain.S3File.service.impl;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.nbc.account.trollo.domain.S3File.entity.S3File;
import org.nbc.account.trollo.domain.S3File.repository.S3FileRepository;
import org.nbc.account.trollo.domain.S3File.service.S3FileService;
import org.nbc.account.trollo.domain.card.entity.Card;
import org.nbc.account.trollo.domain.card.repository.CardRepository;
import org.nbc.account.trollo.global.util.S3Provider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3FileServiceImpl implements S3FileService {

    private final S3FileRepository s3FileRepository;
    private final CardRepository cardRepository;
    private final S3Provider s3Provider;
    @Value("${cloud.aws.s3.bucket.url}")
    private String url;

    public void saveFile(Long cardId, MultipartFile multipartFile) throws IOException {
        String fileUrl = s3Provider.saveFile(multipartFile);
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        String filePath = url;
        Card card = cardRepository.findCardById(cardId);
        s3FileRepository.save(S3File.builder()
            .card(card)
            .filePath(filePath)
            .fileName(fileName)
            .build());
    }

/*    public void updateFile(Long cardId,MultipartFile multipartFile) throws IOException{
        String imageUrl;
        Card card = cardRepository.findCardById(cardId);
        String originalFilename = null;
    }*/


}
