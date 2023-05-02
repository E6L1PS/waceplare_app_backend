package com.itacademy.waceplare.service;

import com.itacademy.waceplare.model.Ad;

import java.util.List;

public interface IFavoriteAdService {
    List<Ad> getAll();

    List<Long> getAdsId();

    List<Ad> getAllByTitle(String title);

    void addByAdId(Long adId);

    void deleteByAdId(Long adId);

    void deleteFavorites();

    void deleteInactiveFavorites();

}
