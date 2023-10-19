package com.itacademy.waceplare.repository;

import com.itacademy.waceplare.model.AdImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdImageRepository extends JpaRepository<AdImage, Long> {

    @Query("SELECT a.url FROM AdImage a WHERE a.ad.id = :adId AND a.isReviewImage = true")
    String findReviewImageUrlByAdId(Long adId);

    @Modifying
    @Query("DELETE FROM AdImage a  WHERE a.ad.id = :adId")
    void deleteAllImageUrlsByAdId(Long adId);
}
