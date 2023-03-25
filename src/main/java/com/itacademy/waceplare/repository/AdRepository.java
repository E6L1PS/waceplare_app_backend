package com.itacademy.waceplare.repository;


import com.itacademy.waceplare.model.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

    List<Ad> findByStatusTrue();

    List<Ad> findByStatusTrueAndTitle(String title);
}
