package com.itacademy.waceplare.service;

import com.itacademy.waceplare.exception.UserNotFoundException;
import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.model.FavoriteAd;
import com.itacademy.waceplare.model.User;
import com.itacademy.waceplare.repository.AdRepository;
import com.itacademy.waceplare.repository.FavoriteAdRepository;
import com.itacademy.waceplare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteAdService implements IFavoriteAdService {

    private final FavoriteAdRepository favoriteAdRepository;

    private final UserRepository userRepository;
    private final AdRepository adRepository;


    @Override
    public List<Ad> getAll(String username) {
        Optional<User> optionalUser = userRepository.findByEmail(username);

        return favoriteAdRepository.findAdsByUserId(optionalUser.get().getId());
    }

    @Override
    public List<Ad> getAllByTitleAndUserId(String name, Long userId) {
        return null;
    }

    @Override
    public List<Ad> getAllByUser(User user) {
        return null;
    }

    @Override
    public void addByAdId(Long adId, String username) {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        Optional<Ad> optionalAd = adRepository.findById(adId);

        if (optionalUser.isPresent() && optionalAd.isPresent()) {
            User user = optionalUser.get();
            Ad ad = optionalAd.get();

            favoriteAdRepository.save(new FavoriteAd(user, ad));
        } else {
            throw new UserNotFoundException("Ad with id " + adId + " not found");
        }

    }

    @Override
    public void deleteByAdId(Long adId, String username) {

    }

}
