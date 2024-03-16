package com.itacademy.waceplare.service.interfaces;


import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    byte[] getReviewImage(Long adId);

    byte[] getImageById(String id);

    void uploadImages(Long adId, List<MultipartFile> images);

}
