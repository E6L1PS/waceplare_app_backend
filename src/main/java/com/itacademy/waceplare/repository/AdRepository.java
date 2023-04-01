package com.itacademy.waceplare.repository;


import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

    List<Ad> findByStatusTrue();

    List<Ad> findByUser(User user);
/*

    List<Ad> findByStatusTrueAndTitle(String title);
*/
    @Query("SELECT ad FROM Ad ad WHERE ad.status=true AND ad.title ILIKE %:title%")
    List<Ad> findByStatusTrueAndTitle(String title);
}
