package com.itacademy.waceplare.repository;


import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

    Page<Ad> findByStatusTrue(PageRequest pageRequest);

    @Query("SELECT a FROM Ad a WHERE a.status=true AND a.title ILIKE %:title%")
    Page<Ad> findByStatusTrueAndTitle(String title, PageRequest pageRequest);

    Page<Ad> findByUser(User user, PageRequest pageRequest);

    @Modifying
    @Query("UPDATE Ad a SET a.views = a.views + 1 WHERE a.id = :adId")
    void incrementViewCount(Long adId);

    @Modifying
    @Query("UPDATE Ad a SET a.favorites = a.favorites + 1 WHERE a.id = :adId")
    void incrementFavoriteCount(Long adId);

    @Modifying
    @Query("UPDATE Ad a SET a.favorites = a.favorites - 1 WHERE a.id = :adId")
    void decrementFavoriteCount(Long adId);

    @Modifying
    @Query("UPDATE Ad a SET a.status = :status WHERE a.user.id = :userId AND a.id = :adId")
    void updateAdStatusByUser(Long adId, Long userId, Boolean status);

    @Modifying
    @Query("DELETE FROM Ad a WHERE a.user.id = :userId AND a.id = :adId")
    void deleteAdByUserId(Long adId, Long userId);

}
