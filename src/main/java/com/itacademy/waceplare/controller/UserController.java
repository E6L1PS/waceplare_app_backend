package com.itacademy.waceplare.controller;

import com.itacademy.waceplare.model.User;
import com.itacademy.waceplare.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;


    @GetMapping("/all")
    private List<User> getUsers() {
        return userService.getAll();
    }

    
}
