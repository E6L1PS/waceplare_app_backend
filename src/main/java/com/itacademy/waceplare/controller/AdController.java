package com.itacademy.waceplare.controller;

import com.itacademy.waceplare.dto.AdDTO;
import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.repository.AdImageRepository;
import com.itacademy.waceplare.service.IAdService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ads")
@RequiredArgsConstructor
public class AdController {

    private final IAdService adService;
    private final AdImageRepository adImageRepository;

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

    @GetMapping("/{adId}")
    public Ad getAdById(@PathVariable Long adId) {
        return adService.getAdById(adId);
    }

    @PreAuthorize("hasRole(Role.USER.name())")
    @PostMapping
    public Long postAd(@RequestBody AdDTO adDto) {
        return adService.postAd(adDto);
    }

    @PreAuthorize("hasRole(Role.USER.name())")
    @PostMapping("/{adId}/images")
    public void uploadImages(@PathVariable Long adId, @RequestParam("files") List<MultipartFile> files) {
        try {
            adService.uploadImages(adId, files);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{adId}/image")
    public byte[] getReviewImage(@PathVariable Long adId) throws IOException {
        String url = adImageRepository.findUrlByAdId(adId);
        File file = ResourceUtils.getFile(url);
        InputStream in = new FileInputStream(file);
        return Files.readAllBytes(file.toPath());
    }

    @GetMapping("/image")
    public byte[] getImage(@RequestParam("url") String url) throws IOException {
        File file = ResourceUtils.getFile(url);
        InputStream in = new FileInputStream(file);
        return Files.readAllBytes(file.toPath());
    }


    @PreAuthorize("hasRole(Role.USER.name())")
    @DeleteMapping("/{adId}")
    public void deleteAd(@PathVariable Long adId) {
        adService.deleteAd(adId);
    }

    @PreAuthorize("hasRole(Role.USER.name())")
    @PutMapping("/my/{adId}/hide")
    public void hideAd(@PathVariable Long adId) {
        adService.hideAd(adId);
    }

    @PreAuthorize("hasRole(Role.USER.name())")
    @PutMapping("/my/{adId}/show")
    public void showAd(@PathVariable Long adId) {
        adService.showAd(adId);
    }


}
