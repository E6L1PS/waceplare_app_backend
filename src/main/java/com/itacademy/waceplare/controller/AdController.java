package com.itacademy.waceplare.controller;

import com.itacademy.waceplare.dto.AdDTO;
import com.itacademy.waceplare.mapper.AdMapper;
import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.model.User;
import com.itacademy.waceplare.service.interfaces.IAdImageService;
import com.itacademy.waceplare.service.interfaces.IAdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/ads")
@RequiredArgsConstructor
public class AdController {

    private final IAdService adService;
    private final IAdImageService adImageService;
    private final AdMapper adMapper;

    @GetMapping
    public List<Ad> getAds(
            @RequestParam(required = false, defaultValue = "0") Integer page,
                @RequestParam(required = false, defaultValue = "10") Integer size) {
        return adService.getAll(PageRequest.of(page, size, Sort.by("id").descending()));
    }

    @GetMapping("/title")
    public List<Ad> getAdsByTitle(
            @RequestParam("title") String title,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return adService.getAllByTitle(title, PageRequest.of(page, size, Sort.by("id").descending()));
    }

    @PreAuthorize("hasRole(Role.USER.name())")
    @GetMapping("/my")
    public List<Ad> getAdsByUser(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return adService.getAdsByUser(PageRequest.of(page, size, Sort.by("id").descending()));
    }

    @GetMapping("/{adId}")
    public Ad getAdById(@PathVariable Long adId) {
        return adService.getAdById(adId);
    }

    @PreAuthorize("hasRole(Role.USER.name())")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long postAd(@RequestBody AdDTO adDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("UserId: " + user.getId());
        Ad ad = adMapper.toAd(adDto);
        ad.setUser(user);
        return adService.postAd(ad);
    }

    @PreAuthorize("hasRole(Role.USER.name())")
    @DeleteMapping("/{adId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAd(@PathVariable Long adId) {
        adService.deleteAd(adId);
    }

    @PreAuthorize("hasRole(Role.USER.name())")
    @PutMapping("/my/{adId}/hide")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void hideAd(@PathVariable Long adId) {
        adService.hideAd(adId);
    }

    @PreAuthorize("hasRole(Role.USER.name())")
    @PutMapping("/my/{adId}/show")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void showAd(@PathVariable Long adId) {
        adService.showAd(adId);
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
        return adImageService.getReviewImage(adId);
    }

    @GetMapping("/image")
    public byte[] getImage(@RequestParam("url") String url) throws IOException {
        return adImageService.getImageByUrl(url);
    }


/*    public void getReviewImage(@PathVariable Long adId, HttpServletResponse httpServletResponse) throws IOException {

        String url = adImageRepository.findUrlByAdId(adId);
        File file = ResourceUtils.getFile(url);
        InputStream in = new FileInputStream(file);
        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(in, httpServletResponse.getOutputStream());
    }*/

}