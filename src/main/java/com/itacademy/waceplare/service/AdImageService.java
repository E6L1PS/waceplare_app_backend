package com.itacademy.waceplare.service;

import com.itacademy.waceplare.repository.AdImageRepository;
import com.itacademy.waceplare.service.interfaces.IAdImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdImageService implements IAdImageService {

    private final AdImageRepository adImageRepository;

    @Override
    public byte[] getReviewImage(Long adId) throws IOException {
        String url = adImageRepository.findReviewImageUrlByAdId(adId);

        if (url == null) {
            throw new FileNotFoundException("Image with ad id " + adId + " not found.");
        }

        return  Files.readAllBytes(Path.of(url));
    }

    @Override
    public byte[] getImageByUrl(String url) throws IOException {
        return Files.readAllBytes(Path.of(url));
    }

}