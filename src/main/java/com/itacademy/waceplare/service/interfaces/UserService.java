package com.itacademy.waceplare.service.interfaces;

import com.itacademy.waceplare.dto.UserInfoDto;
import com.itacademy.waceplare.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    UserInfoDto getUserInfo();

    void rateUser(Long userId, Integer newRating);
}
