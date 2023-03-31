package com.itacademy.waceplare.service;

import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.model.User;

import java.util.List;

public interface IFavoriteAdService {
    List<Ad> getAll();

    List<Ad> getAllByTitle(String title);

    void addByAdId(Long adId);

    void deleteByAdId(Long adId);


}
