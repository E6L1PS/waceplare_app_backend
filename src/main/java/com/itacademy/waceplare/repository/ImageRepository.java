package com.itacademy.waceplare.repository;

import com.itacademy.waceplare.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("SELECT i FROM Image i WHERE i.ad.id = :adId AND i.isReviewImage = true")
    Optional<Image> findReviewImageByAdId(Long adId);

//    @Modifying
//    @Query("DELETE FROM AdImage a  WHERE a.ad.id = :adId")
//    void deleteAllImageUrlsByAdId(Long adId);
}
