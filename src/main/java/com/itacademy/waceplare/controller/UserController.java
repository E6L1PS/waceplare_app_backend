package com.itacademy.waceplare.controller;

import com.itacademy.waceplare.dto.UserInfoDto;
import com.itacademy.waceplare.model.User;
import com.itacademy.waceplare.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    private List<User> getUsers() {
        return userService.getAll();
    }

    @PreAuthorize("hasRole(Role.USER.name())")
    @GetMapping
    private UserInfoDto getUserInfo() {
        return userService.getUserInfo();
    }

    @PreAuthorize("hasRole(Role.USER.name())")
    @PutMapping("/rate/{userId}")
    private void rateUser(@PathVariable Long userId, @RequestParam Integer newRating) {
        userService.rateUser(userId, newRating);
    }

}
