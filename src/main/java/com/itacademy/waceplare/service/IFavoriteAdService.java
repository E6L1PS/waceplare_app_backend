package com.itacademy.waceplare.service;

import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.model.User;

import java.util.List;

public interface IFavoriteAdService {
    List<Ad> getAll(String username);

    List<Ad> getAllByTitleAndUserId(String name, Long userId);

    List<Ad> getAllByUser(User user);


    void addByAdId(Long adId, String username);

    void deleteByAdId(Long adId, String username);


}
