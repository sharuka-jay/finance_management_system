package com.example.user_service.controller;

import com.example.user_service.model.User;
import com.example.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public Integer registerUser(@RequestBody User user){
       int userId =  userService.registerUser(user);
       return userId;
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody User user){
        User user1 =  userService.loginUser(user);
        return user1;
    }
    
}
