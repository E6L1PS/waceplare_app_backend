package com.itacademy.waceplare.repository;

import com.itacademy.waceplare.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
//
//    @Query("SELECT a.url FROM AdImage a WHERE a.ad.id = :adId AND a.isReviewImage = true")
//    String findReviewImageUrlByAdId(Long adId);
//
//    @Modifying
//    @Query("DELETE FROM AdImage a  WHERE a.ad.id = :adId")
//    void deleteAllImageUrlsByAdId(Long adId);
}
