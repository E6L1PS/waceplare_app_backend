package com.itacademy.waceplare.service.interfaces;


import java.io.IOException;

public interface IAdImageService {

    byte[] getReviewImage(Long adId) throws IOException;

    byte[] getImageByUrl(String url) throws IOException;
}
