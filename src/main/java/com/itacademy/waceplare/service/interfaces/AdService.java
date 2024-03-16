package com.itacademy.waceplare.service.interfaces;

import com.itacademy.waceplare.model.Ad;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface AdService {

    List<Ad> getAll(PageRequest pageRequest);

    List<Ad> getAllByTitle(String title, PageRequest pageRequest);

    List<Ad> getAdsByUser(PageRequest pageRequest);

    Long postAd(Ad ad);

    void deleteAd(Long adId);

    void hideAd(Long adId);

    void showAd(Long adId);

    Ad getAdById(Long adId);

}
