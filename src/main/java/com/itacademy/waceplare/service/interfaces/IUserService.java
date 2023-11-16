package com.itacademy.waceplare.service.interfaces;

import com.itacademy.waceplare.dto.UserInfo;
import com.itacademy.waceplare.model.User;

import java.util.List;

public interface IUserService {

    List<User> getAll();

    UserInfo getUserInfo();

    void rateUser(Long userId, Integer newRating);
}
