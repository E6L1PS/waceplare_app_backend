package com.itacademy.waceplare.service.interfaces;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    byte[] getReviewImage(Long adId) throws IOException;

    byte[] getImageByUrl(String url) throws IOException;

    void uploadImages(Long adId, List<MultipartFile> images) throws IOException;

}
