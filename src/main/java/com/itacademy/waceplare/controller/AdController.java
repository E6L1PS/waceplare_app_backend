package com.itacademy.waceplare.controller;

import com.itacademy.waceplare.dto.AdDto;
import com.itacademy.waceplare.mapper.AdMapper;
import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.model.User;
import com.itacademy.waceplare.service.ImageServiceImpl;
import com.itacademy.waceplare.service.interfaces.AdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/ads")
@RequiredArgsConstructor
public class AdController {

    private final AdService adService;
    //    private final AdImageServiceImpl adImageService;
    private final ImageServiceImpl adImageServiceTest;
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
    public Long postAd(@RequestBody AdDto adDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("UserId: " + user.getId());
        Ad ad = adMapper.toEntity(adDto);
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
    @PostMapping(value = "/{adId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadImages(
            @PathVariable Long adId,
            @RequestPart List<MultipartFile> files
    ) {
        adImageServiceTest.uploadImages(adId, files);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    @GetMapping("/{adId}/image")
//    public byte[] getReviewImage(@PathVariable Long adId) throws IOException {
//        return adImageService.getReviewImage(adId);
//    }
//
//    @GetMapping("/image")
//    public byte[] getImage(@RequestParam("url") String url) throws IOException {
//        return adImageService.getImageByUrl(url);
//    }


/*    public void getReviewImage(@PathVariable Long adId, HttpServletResponse httpServletResponse) throws IOException {

        String url = adImageRepository.findUrlByAdId(adId);
        File file = ResourceUtils.getFile(url);
        InputStream in = new FileInputStream(file);
        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(in, httpServletResponse.getOutputStream());
    }*/

}