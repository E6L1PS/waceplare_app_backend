package com.itacademy.waceplare.service.interfaces;

import com.itacademy.waceplare.dto.AdDTO;
import com.itacademy.waceplare.model.Ad;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IAdService {
    List<Ad> getAll();

    List<Ad> getAllByTitle(String title);

    List<Ad> getAdsByUser();

    Long postAd(Ad ad);

    void uploadImages(Long adId, List<MultipartFile> images) throws IOException;

    void deleteAd(Long adId);

    void hideAd(Long adId);

    void showAd(Long adId);

    Ad getAdById(Long adId);
}
