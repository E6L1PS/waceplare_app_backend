package com.itacademy.waceplare.controller;

import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.service.IAdService;
import com.itacademy.waceplare.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdController {

    @Autowired
    private IAdService adService;

    @Autowired
    private IImageService imageService;

    @GetMapping
    public List<Ad> getAds() {
        return adService.getAll();
    }

}
