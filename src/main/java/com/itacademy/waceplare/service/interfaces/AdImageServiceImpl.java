//package com.itacademy.waceplare.service;
//
//import com.itacademy.waceplare.exception.AdNotFoundException;
//import com.itacademy.waceplare.model.Ad;
//import com.itacademy.waceplare.model.AdImage;
//import com.itacademy.waceplare.repository.AdImageRepository;
//import com.itacademy.waceplare.repository.AdRepository;
//import com.itacademy.waceplare.service.interfaces.AdImageService;
//import io.minio.MinioClient;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.server.UnsupportedMediaTypeStatusException;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.util.*;
//
//@Slf4j
//@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//public class AdImageServiceImpl implements AdImageService {
//
//    @Value("${upload.path}")
//    private String uploadPath;
//    private final AdImageRepository adImageRepository;
//
//    private final AdRepository adRepository;
//
//    @Override
//    public byte[] getReviewImage(Long adId) throws IOException {
//        String url = adImageRepository.findReviewImageUrlByAdId(adId);
//
//        if (url == null) {
//            throw new FileNotFoundException("Image with ad id " + adId + " not found.");
//        }
//
//        return Files.readAllBytes(Path.of(url));
//    }
//
//    @Override
//    public byte[] getImageByUrl(String url) throws IOException {
//        return Files.readAllBytes(Path.of(url));
//    }
//
//    @Override
//    @Transactional
//    public void uploadImages(Long adId, List<MultipartFile> images) throws IOException {
//        Optional<Ad> optionalAd = adRepository.findById(adId);
//
//        if (optionalAd.isPresent()) {
//            List<String> imagePaths = new ArrayList<>();
//
//            for (MultipartFile image : images) {
//                if (Objects.requireNonNull(image.getContentType()).startsWith("image/")) {
//                    String imagePath = saveImage(adId, image); // Сохраняем изображение на сервере и получаем путь к нему
//                    imagePaths.add(imagePath);
//                } else {
//                    throw new UnsupportedMediaTypeStatusException("File type is not supported.");
//                }
//            }
//
//            List<AdImage> adImages = new ArrayList<>();
////
////            boolean isFirstElement = true;
////            for (String imagePath : imagePaths) {
////                AdImage adImage = AdImage.builder()
////                        .uuid(imagePath)
////                        .ad(optionalAd.get())
////                        .build();
////
////                if (isFirstElement) {
////                    adImage.setIsReviewImage(true);
////                    isFirstElement = false;
////                } else {
////                    adImage.setIsReviewImage(false);
////                }
////
////                adImages.add(adImage);
////            }
//
//            adImageRepository.saveAll(adImages);
//        } else {
//            throw new AdNotFoundException("Ad with id " + adId + " not found");
//        }
//    }
//
//    private String saveImage(Long adId, MultipartFile image) throws IOException {
//        String imageName = UUID.randomUUID() + "-" + image.getOriginalFilename();
//        Path adDirPath = Paths.get(uploadPath, adId.toString());
//
//        if (!Files.exists(adDirPath)) {
//            Files.createDirectories(adDirPath);
//        }
//
//        Path imagePath = adDirPath.resolve(imageName);
//        Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
//        return imagePath.toString();
//    }
//
//}