package com.itacademy.waceplare.service;

import com.itacademy.waceplare.dto.UserInfo;
import com.itacademy.waceplare.exception.AdNotFoundException;
import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.model.AdImage;
import com.itacademy.waceplare.model.User;
import com.itacademy.waceplare.repository.AdImageRepository;
import com.itacademy.waceplare.repository.AdRepository;
import com.itacademy.waceplare.service.interfaces.IAdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdService implements IAdService {

    @Value("${upload.path}")
    private String uploadPath;

    private final AdRepository adRepository;
    private final AdImageRepository adImageRepository;

    @Override
    public List<Ad> getAll(PageRequest pageRequest) {
        Page<Ad> page = adRepository.findByStatusTrue(pageRequest);
        return page.getContent();
    }

    @Override
    public List<Ad> getAllByTitle(String title, PageRequest pageRequest) {
        Page<Ad> page = adRepository.findByStatusTrueAndTitle(title, pageRequest);
        return page.getContent();
    }

    @Override
    public List<Ad> getAdsByUser(PageRequest pageRequest) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<Ad> page = adRepository.findByUser(user, pageRequest);
        return page.getContent();
    }

    @Override
    @Transactional
    public Long postAd(Ad ad) {
        return adRepository.save(ad).getId();
    }

    @Override
    @Transactional
    public void uploadImages(Long adId, List<MultipartFile> images) throws IOException {
        Optional<Ad> optionalAd = adRepository.findById(adId);

        if (optionalAd.isPresent()) {
            List<String> imagePaths = new ArrayList<>();

            for (MultipartFile image : images) {
                if (Objects.requireNonNull(image.getContentType()).startsWith("image/")) {
                    String imagePath = saveImage(adId, image); // Сохраняем изображение на сервере и получаем путь к нему
                    imagePaths.add(imagePath);
                } else {
                    throw new UnsupportedMediaTypeStatusException("File type is not supported.");
                }
            }

            List<AdImage> adImages = new ArrayList<>();

            boolean isFirstElement = true;
            for (String imagePath : imagePaths) {
                AdImage adImage = AdImage.builder()
                        .url(imagePath)
                        .ad(optionalAd.get())
                        .build();

                if (isFirstElement) {
                    adImage.setIsReviewImage(true);
                    isFirstElement = false;
                } else {
                    adImage.setIsReviewImage(false);
                }

                adImages.add(adImage);
            }

            adImageRepository.saveAll(adImages);
        } else {
            throw new AdNotFoundException("Ad with id " + adId + " not found");
        }
    }


    @Override
    @Transactional
    public void deleteAd(Long adId) {
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        adImageRepository.deleteAllImageUrlsByAdId(adId);
        adRepository.deleteAdByUserId(adId, userId);
    }

    @Override
    @Transactional
    public void hideAd(Long adId) {
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        adRepository.updateAdStatusByUser(adId, userId, false);
    }

    @Override
    @Transactional
    public void showAd(Long adId) {
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        adRepository.updateAdStatusByUser(adId, userId, true);
    }

    @Override
    @Transactional
    public Ad getAdById(Long adId) {
        Optional<Ad> optionalAd = adRepository.findById(adId);
        if (optionalAd.isPresent()) {
            adRepository.incrementViewCount(adId);
            Ad ad = optionalAd.get();
            User user = ad.getUser();
            UserInfo userInfo = UserInfo.builder()
                    .id(user.getId())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .email(user.getEmail())
                    .number(user.getNumber())
                    .rating(user.getRating())
                    .dateOfCreated(user.getDateOfCreated())
                    .build();
            ad.setUserInfo(userInfo);
            return ad;
        } else {
            throw new AdNotFoundException("Ad with id " + adId + " not found");
        }
    }

    private String saveImage(Long adId, MultipartFile image) throws IOException {
        String imageName = UUID.randomUUID() + "-" + image.getOriginalFilename();
        Path adDirPath = Paths.get(uploadPath, adId.toString());

        if (!Files.exists(adDirPath)) {
            Files.createDirectories(adDirPath);
        }

        Path imagePath = adDirPath.resolve(imageName);
        Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
        return imagePath.toString();
    }
}
