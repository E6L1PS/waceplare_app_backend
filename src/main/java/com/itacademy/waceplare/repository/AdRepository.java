package com.itacademy.waceplare.repository;


import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

    List<Ad> findByStatusTrue();

    List<Ad> findByUser(User user);

    @Query("SELECT a FROM Ad a WHERE a.status=true AND a.title ILIKE %:title%")
    List<Ad> findByStatusTrueAndTitle(String title);

    @Modifying
    @Query("UPDATE Ad a SET a.views = a.views + 1 WHERE a.id = :adId")
    void incrementViewCount(Long adId);

    @Modifying
    @Query("UPDATE Ad a SET a.status = :status WHERE a.user.id = :userId AND a.id = :adId")
    void updateAdStatusByUser(Long adId, Long userId, Boolean status);

    @Modifying
    @Query("DELETE FROM Ad a WHERE a.user.id = :userId AND a.id = :adId")
    void deleteAdByUserId(Long adId, Long userId);

}
