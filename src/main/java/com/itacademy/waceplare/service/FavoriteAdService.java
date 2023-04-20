package com.itacademy.waceplare.service;

import com.itacademy.waceplare.exception.UserNotFoundException;
import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.model.FavoriteAd;
import com.itacademy.waceplare.model.User;
import com.itacademy.waceplare.repository.AdRepository;
import com.itacademy.waceplare.repository.FavoriteAdRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FavoriteAdService implements IFavoriteAdService {

    private final FavoriteAdRepository favoriteAdRepository;
    private final AdRepository adRepository;

    @Override
    public List<Ad> getAll() {
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        return favoriteAdRepository.findAdsByUserId(userId);
    }

    @Override
    public List<Long> getAdsId() {
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        return favoriteAdRepository.findAdsIdByUserId(userId);
    }

    @Override
    public List<Ad> getAllByTitle(String title) {
        return null;
    }

    @Override
    @Transactional
    public void addByAdId(Long adId) {
        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Optional<Ad> optionalAd = adRepository.findById(adId);

        if (optionalAd.isPresent()) {
            Ad ad = optionalAd.get();

            favoriteAdRepository.save(new FavoriteAd(user, ad));
        } else {
            throw new UserNotFoundException("Ad with id " + adId + " not found");
        }

    }

    @Override
    @Transactional
    public void deleteByAdId(Long adId) {
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        favoriteAdRepository.deleteByUserIdAndByAdId(userId, adId);
    }

}
