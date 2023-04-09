package com.itacademy.waceplare.service;

import com.itacademy.waceplare.dto.AdDTO;
import com.itacademy.waceplare.model.Ad;

import java.util.List;

public interface IAdService {
    List<Ad> getAll();

    List<Ad> getAllByTitle(String title);

    List<Ad> getAdsByUser();

    void postAd(AdDTO adDTO);

    void deleteAd(Long adId);

    void hideAd(Long adId);

    void showAd(Long adId);

    Ad getAdById(Long adId);
}
