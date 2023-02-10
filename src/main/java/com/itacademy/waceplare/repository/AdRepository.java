package com.itacademy.waceplare.repository;


import com.itacademy.waceplare.model.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

    Ad findByName(String name);

}
