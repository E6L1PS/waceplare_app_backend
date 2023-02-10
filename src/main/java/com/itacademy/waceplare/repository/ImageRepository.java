package com.itacademy.waceplare.repository;

import com.itacademy.waceplare.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
