package com.itacademy.waceplare.controller;

import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.service.interfaces.IFavoriteAdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/favorites")
@RequiredArgsConstructor
public class FavoriteAdController {

    private final IFavoriteAdService favoriteAdService;

    @PreAuthorize("hasRole(Role.USER.name())")
    @GetMapping
    public List<Ad> getAds() {
        return favoriteAdService.getAll();
    }

    @PreAuthorize("hasRole(Role.USER.name())")
    @GetMapping("/id")
    public List<Long> getAdsId() {
        return favoriteAdService.getAdsId();
    }

    @PreAuthorize("hasRole(Role.USER.name())")
    @GetMapping("/title")
    public List<Ad> getAdsByTitle(@RequestParam("title") String title) {
        return favoriteAdService.getAllByTitle(title);
    }

    @PreAuthorize("hasRole(Role.USER.name())")
    @PostMapping("{adId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addFavoriteAd(@PathVariable Long adId) {
        favoriteAdService.addByAdId(adId);
    }

    @PreAuthorize("hasRole(Role.USER.name())")
    @DeleteMapping("{adId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFavoriteAd(@PathVariable Long adId) {
        favoriteAdService.deleteByAdId(adId);
    }

    @PreAuthorize("hasRole(Role.USER.name())")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFavorites() {
        favoriteAdService.deleteFavorites();
    }

    @PreAuthorize("hasRole(Role.USER.name())")
    @DeleteMapping("inactive")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInactiveFavorites() {
        favoriteAdService.deleteInactiveFavorites();
    }
}
