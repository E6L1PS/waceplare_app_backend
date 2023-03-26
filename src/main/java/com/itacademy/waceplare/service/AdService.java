package com.itacademy.waceplare.service;

import com.itacademy.waceplare.dto.AdDTO;
import com.itacademy.waceplare.exception.UserNotFoundException;
import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.model.Category;
import com.itacademy.waceplare.model.User;
import com.itacademy.waceplare.repository.AdRepository;
import com.itacademy.waceplare.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
public class AdService implements IAdService {

    @Autowired
    private AdRepository adRepository;
    @Autowired
    private UserRepository userRepository;

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
        Optional<User> optionalUser = userRepository.findById(adDTO.getUserId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Ad ad = new Ad(
                    adDTO.getPrice(),
                    adDTO.getTitle(),
                    adDTO.getDescription(),
                    adDTO.getCategory(),
                    user
            );

            adRepository.save(ad);
        } else {
            throw new UserNotFoundException("User with id " + adDTO.getUserId() + " not found");
        }
    }

    @Override
    @Transactional
    public void delete(Ad ad) {
        log.info("saving new ad {}", ad);
        adRepository.delete(ad);
    }

}
