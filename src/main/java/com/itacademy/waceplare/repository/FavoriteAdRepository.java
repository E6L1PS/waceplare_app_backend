package com.itacademy.waceplare.repository;

import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.model.FavoriteAd;
import com.itacademy.waceplare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FavoriteAdRepository extends JpaRepository<FavoriteAd, Long> {

    @Query("SELECT fa.ad FROM FavoriteAd fa WHERE fa.user.id = :userId")
    List<Ad> findAdsByUserId(Long userId);

    @Query("SELECT fa.ad.id FROM FavoriteAd fa WHERE fa.user.id = :userId")
    List<Long> findAdsIdByUserId(Long userId);

    @Modifying
    @Query("DELETE FROM FavoriteAd fa WHERE fa.user.id = :userId AND fa.ad.id = :adId")
    void deleteByUserIdAndByAdId(Long userId, Long adId);


    @Modifying
    @Query("DELETE FROM FavoriteAd fa WHERE fa.user.id = :userId")
    void deleteAllByUserId(Long userId);


    @Modifying
    @Query("DELETE FROM FavoriteAd fa WHERE fa.user.id = :userId AND fa.ad.status = false")
    void deleteAllInactiveByUserId(Long userId);

}
