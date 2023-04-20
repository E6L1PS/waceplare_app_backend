package com.itacademy.waceplare.service;

import com.itacademy.waceplare.dto.UserInfo;
import com.itacademy.waceplare.model.User;

import java.util.List;

public interface IUserService {

    List<User> getAll();

    UserInfo getUserInfo();

/*    UserCredentials save(UserCredentials userCredentials);

    UserCredentials findByUsername(String username);

    UserCredentials findByEmail(String email);*/
}
