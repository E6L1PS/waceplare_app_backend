package com.itacademy.waceplare.service;

import com.itacademy.waceplare.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IImageService {

    Image toImageEntity(MultipartFile file) throws IOException;
}
