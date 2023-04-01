package com.itacademy.waceplare.service;

import com.itacademy.waceplare.dto.AdDTO;
import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.model.User;
import com.itacademy.waceplare.repository.AdRepository;
import com.itacademy.waceplare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                adDTO.getCategory(),
                user
        );

        adRepository.save(ad);
    }

    @Override
    @Transactional
    public void deleteByAdId(Long adId) {
     /*   log.info("saving new ad {}", ad);
        adRepository.delete(ad);*/
    }

}
