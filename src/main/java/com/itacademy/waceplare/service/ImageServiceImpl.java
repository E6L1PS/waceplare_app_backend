package com.itacademy.waceplare.service;

import com.itacademy.waceplare.exception.AdNotFoundException;
import com.itacademy.waceplare.exception.ImageUploadException;
import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.model.Image;
import com.itacademy.waceplare.repository.ImageRepository;
import com.itacademy.waceplare.repository.AdRepository;
import com.itacademy.waceplare.service.interfaces.ImageService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Создан: 15.03.2024.
 *
 * @author Pesternikov Danil
 */
@Transactional
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    private final AdRepository adRepository;

    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucketName;

    @Override
    public byte[] getReviewImage(Long adId) throws IOException {
        //TODO getReviewImage
        return new byte[0];
    }

    @Override
    public byte[] getImageByUrl(String url) throws IOException {
        //TODO getImageByUrl
        return new byte[0];
    }

    @Override
    public void uploadImages(Long adId, List<MultipartFile> images) {
        Ad ad = adRepository.findById(adId)
                .orElseThrow(() -> new AdNotFoundException("Ad wit id %s not found".formatted(adId)));
        images.forEach(image -> {
            try {
                Image build = Image.builder()
                        .isReviewImage(false)
                        .ad(ad)
                        .build();
                UUID id = imageRepository.save(build).getId();
                uploadImage(id, image);
            } catch (Exception e) {
                throw new ImageUploadException(e.getMessage());
            }
        });
    }

    @SneakyThrows
    private void uploadImage(UUID uuid, MultipartFile image) {
        minioClient.putObject(PutObjectArgs.builder()
                .stream(image.getInputStream(), image.getSize(), -1)
                .contentType(image.getContentType())
                .bucket(bucketName)
                .object(uuid.toString())
                .build());
    }

}
