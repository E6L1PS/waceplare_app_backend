package com.itacademy.waceplare.service.interfaces;

import com.itacademy.waceplare.model.Ad;

import java.util.List;

public interface FavoriteAdService {
    List<Ad> getAll();

    List<Long> getAdsId();

    List<Ad> getAllByTitle(String title);

    void addByAdId(Long adId);

    void deleteByAdId(Long adId);

    void deleteFavorites();

    void deleteInactiveFavorites();

}
