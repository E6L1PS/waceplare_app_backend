package com.itacademy.waceplare.service;

import com.itacademy.waceplare.dto.UserInfoDto;
import com.itacademy.waceplare.exception.AdNotFoundException;
import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.model.User;
import com.itacademy.waceplare.repository.ImageRepository;
import com.itacademy.waceplare.repository.AdRepository;
import com.itacademy.waceplare.service.interfaces.AdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {


    private final AdRepository adRepository;
    private final ImageRepository imageRepository;

    @Override
    public List<Ad> getAll(PageRequest pageRequest) {
        Page<Ad> page = adRepository.findByStatusTrue(pageRequest);
        return page.getContent();
    }

    @Override
    public List<Ad> getAllByTitle(String title, PageRequest pageRequest) {
        Page<Ad> page = adRepository.findByStatusTrueAndTitle(title, pageRequest);
        return page.getContent();
    }

    @Override
    public List<Ad> getAdsByUser(PageRequest pageRequest) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<Ad> page = adRepository.findByUser(user, pageRequest);
        return page.getContent();
    }

    @Override
    @Transactional
    public Long postAd(Ad ad) {
        return adRepository.save(ad).getId();
    }




    @Override
    @Transactional
    public void deleteAd(Long adId) {
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        //adImageRepository.deleteAllImageUrlsByAdId(adId);
        adRepository.deleteAdByUserId(adId, userId);
    }

    @Override
    @Transactional
    public void hideAd(Long adId) {
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        adRepository.updateAdStatusByUser(adId, userId, false);
    }

    @Override
    @Transactional
    public void showAd(Long adId) {
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        adRepository.updateAdStatusByUser(adId, userId, true);
    }

    @Override
    @Transactional
    public Ad getAdById(Long adId) {
        Optional<Ad> optionalAd = adRepository.findById(adId);
        if (optionalAd.isPresent()) {
            adRepository.incrementViewCount(adId);
            Ad ad = optionalAd.get();
            User user = ad.getUser();
            UserInfoDto userInfoDto = UserInfoDto.builder()
                    .id(user.getId())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .email(user.getEmail())
                    .number(user.getNumber())
                    .rating(user.getRating())
                    .dateOfCreated(user.getDateOfCreated())
                    .build();
            ad.setUserInfoDto(userInfoDto);
            return ad;
        } else {
            throw new AdNotFoundException("Ad with id " + adId + " not found");
        }
    }


}
