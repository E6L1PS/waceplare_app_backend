package com.itacademy.waceplare.controller;

import com.itacademy.waceplare.dto.UserInfo;
import com.itacademy.waceplare.model.User;
import com.itacademy.waceplare.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("/all")
    private List<User> getUsers() {
        return userService.getAll();
    }

    @PreAuthorize("hasRole(Role.USER.name())")
    @GetMapping
    private UserInfo getUserInfo() {
        return userService.getUserInfo();
    }
    
}
