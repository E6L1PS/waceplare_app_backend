package com.itacademy.waceplare.service;

import com.itacademy.waceplare.dto.AdDTO;
import com.itacademy.waceplare.dto.UserInfo;
import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.model.AdImage;
import com.itacademy.waceplare.model.User;
import com.itacademy.waceplare.repository.AdImageRepository;
import com.itacademy.waceplare.repository.AdRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdService implements IAdService {

    @Value("${upload.path}")
    private String uploadPath;

    private final AdRepository adRepository;
    private final AdImageRepository adImageRepository;

    @Override
    public List<Ad> getAll() {
        return adRepository.findByStatusTrue();
    }

    @Override
    public List<Ad> getAllByTitle(String title) {
        return adRepository.findByStatusTrueAndTitle(title);
    }

    @Override
    public List<Ad> getAdsByUser() {
        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

   /*     // загрузить изображения из папки пользователя
        File imagesFolder = new File("images/" + user.getId());
        File[] imageFiles = imagesFolder.listFiles();
        List<byte[]> images = new ArrayList<>();
        for (File imageFile : imageFiles) {
            BufferedImage image = ImageIO.read(imageFile);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            images.add(baos.toByteArray());
        }*/
        return adRepository.findByUser(user);
    }

    @Override
    @Transactional
    public Long postAd(AdDTO adDTO) {
        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        log.info(user.getId());
        Ad ad = new Ad(
                adDTO.getPrice(),
                adDTO.getTitle(),
                adDTO.getDescription(),
                adDTO.getType(),
                adDTO.getState(),
                user
        );

        return adRepository.save(ad).getId();
    }

    @Override
    @Transactional
    public void uploadImages(Long adId, List<MultipartFile> images) throws IOException {
        Optional<Ad> optionalAd = adRepository.findById(adId);

        if (optionalAd.isPresent()) {
            List<String> imagePaths = new ArrayList<>();

            for (MultipartFile image : images) {
                if (image.getContentType().startsWith("image/")) {
                    String imagePath = saveImage(adId, image); // Сохраняем изображение на сервере и получаем путь к нему
                    imagePaths.add(imagePath);
                } else {
                    throw new UnsupportedMediaTypeStatusException("File type is not supported.");
                }
            }

            List<AdImage> adImages = new ArrayList<>();

            boolean isFirstElement = true;
            for (String imagePath : imagePaths) {
                AdImage adImage = new AdImage();
                adImage.setUrl(imagePath);
                adImage.setAd(optionalAd.get());

                if (isFirstElement) {
                    adImage.setIsReviewImage(true);
                    isFirstElement = false;
                } else {
                    adImage.setIsReviewImage(false);
                }
                adImages.add(adImage);
            }
            adImageRepository.saveAll(adImages);
        }
    }


    @Override
    @Transactional
    public void deleteAd(Long adId) {
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        adImageRepository.deleteAllAdByAd(adId);
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
        adRepository.incrementViewCount(adId);
        Optional<Ad> optionalAd = adRepository.findById(adId);
        if (optionalAd.isPresent()) {
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
            return null;
        }
    }

    private String saveImage(Long adId, MultipartFile image) throws IOException {
        String imageName = UUID.randomUUID().toString() + "-" + image.getOriginalFilename();
        Path adDirPath = Paths.get(uploadPath, adId.toString());

        if (!Files.exists(adDirPath)) {
            Files.createDirectories(adDirPath);
        }

        Path imagePath = adDirPath.resolve(imageName);
        Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
        return imagePath.toString();
    }
}
