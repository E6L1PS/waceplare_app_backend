package com.itacademy.waceplare.controller;

import com.itacademy.waceplare.dto.AdDTO;
import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.service.IAdService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ads")
@RequiredArgsConstructor
public class AdController {

    private final IAdService adService;


    @GetMapping
    public List<Ad> getAds() {
        return adService.getAll();
    }

    @GetMapping("/title")
    public List<Ad> getAdsByTitle(@RequestParam("title") String title) {
        return adService.getAllByTitle(title);
    }

    @PreAuthorize("hasRole(Role.USER.name())")
    @GetMapping("/my")
    public List<Ad> getAdsByUser() {
        return adService.getAdsByUser();
    }

    @PreAuthorize("hasRole(Role.USER.name())")
    @PostMapping
    public void postAd(@RequestBody AdDTO adDto) {
        adService.postAd(adDto);
    }


}
