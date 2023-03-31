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
    private final UserRepository userRepository;

    @Override
    public List<Ad> getAll() {
        return adRepository.findByStatusTrue();
    }

    @Override
    public List<Ad> getAllByTitle(String title) {
        return adRepository.findByStatusTrueAndTitle(title);
    }

    @Override
    @Transactional
    public void add(AdDTO adDTO) {
        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
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
    public void delete(Long adId) {
     /*   log.info("saving new ad {}", ad);
        adRepository.delete(ad);*/
    }

}
