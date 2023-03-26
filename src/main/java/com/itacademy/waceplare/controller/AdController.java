package com.itacademy.waceplare.controller;

import com.itacademy.waceplare.dto.AdDTO;
import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.service.IAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdController {

    @Autowired
    private IAdService adService;

    @GetMapping("/all")
    public List<Ad> getAds() {
        return adService.getAll();
    }

    @PostMapping("/add")
    public void addAd(@RequestBody AdDTO adDto) {
        adService.add(adDto);
    }




}
