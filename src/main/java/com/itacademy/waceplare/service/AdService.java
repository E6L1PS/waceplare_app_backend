package com.itacademy.waceplare.service;

import com.itacademy.waceplare.dto.AdDTO;
import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.model.User;
import com.itacademy.waceplare.repository.AdRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdService implements IAdService {

    private final AdRepository adRepository;

    @Override
    public List<Ad> getAll() {
        return adRepository.findByStatusTrue();
    }

    @Override
    public List<Ad> getAllByTitle(String title) {
        return adRepository.findByStatusTrueAndTitle(title);
    }

    @Override
    public List<Ad> getAdsByUser() {
        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return adRepository.findByUser(user);
    }

    @Override
    @Transactional
    public void postAd(AdDTO adDTO) {
        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        log.info(user.getId());
        Ad ad = new Ad(
                adDTO.getPrice(),
                adDTO.getTitle(),
                adDTO.getDescription(),
                adDTO.getType(),
                adDTO.getState(),
                user
        );

        adRepository.save(ad);
    }

    @Override
    @Transactional
    public void deleteAd(Long adId) {
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        adRepository.deleteAdByUserId(adId, userId);
    }

    @Override
    @Transactional
    public void hideAd(Long adId) {
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        adRepository.updateAdStatusByUser(adId,userId, false);
    }

    @Override
    @Transactional
    public void showAd(Long adId) {
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        adRepository.updateAdStatusByUser(adId,userId, true);
    }

    @Override
    @Transactional
    public Ad getAdById(Long adId) {
        adRepository.incrementViewCount(adId);
        Optional<Ad> ad = adRepository.findById(adId);
        return ad.orElse(null);
    }
}
