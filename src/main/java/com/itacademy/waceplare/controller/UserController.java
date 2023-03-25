package com.itacademy.waceplare.controller;

import com.itacademy.waceplare.model.User;
import com.itacademy.waceplare.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;


    @GetMapping("/all")
    private List<User> getUsers() {
        return userService.getAll();
    }

    
}
