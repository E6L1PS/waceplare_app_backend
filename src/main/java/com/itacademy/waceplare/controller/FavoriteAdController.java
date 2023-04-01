package com.itacademy.waceplare.controller;

import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.security.JwtService;
import com.itacademy.waceplare.service.IFavoriteAdService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favorites")
@RequiredArgsConstructor
public class FavoriteAdController {

    private final IFavoriteAdService favoriteAdService;

    @GetMapping
    public List<Ad> getAds() {
        return favoriteAdService.getAll();
    }

    @GetMapping("/title")
    public List<Ad> getAdsByTitle(@RequestParam("title") String title) {
        return favoriteAdService.getAllByTitle(title);
    }

    @PostMapping("{adId}")
    public void addFavoriteAd(@PathVariable Long adId) {
        favoriteAdService.addByAdId(adId);
    }

    @DeleteMapping("{adId}")
    public void deleteFavoriteAd(@PathVariable Long adId) {
        favoriteAdService.deleteByAdId(adId);
    }

}
