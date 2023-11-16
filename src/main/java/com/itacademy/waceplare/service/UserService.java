package com.itacademy.waceplare.service;

import com.itacademy.waceplare.dto.UserInfo;
import com.itacademy.waceplare.model.User;
import com.itacademy.waceplare.repository.UserRepository;
import com.itacademy.waceplare.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserInfo getUserInfo() {
        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return UserInfo.builder()
                .id(user.getId())
                .email(user.getEmail())
                .number(user.getNumber())
                .rating(user.getRating())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .dateOfCreated(user.getDateOfCreated())
                .build();
    }

    @Transactional
    @Override
    public void rateUser(Long userId, Integer newRating) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Double rating = userRepository.findRatingById(userId);
        if (rating == null) {
            userRepository.updateRatingById(userId, Double.valueOf(newRating));
        } else {
            if (newRating >= 0 && newRating <= 5) {
                rating = (rating + newRating) / 2;
                userRepository.updateRatingById(userId, rating);
            } else {
                throw new IllegalArgumentException("Новый рейтинг должен быть в диапазоне от 0 до 5");
            }
        }
    }


}
