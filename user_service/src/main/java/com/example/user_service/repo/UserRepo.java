package com.example.user_service.repo;

import com.example.user_service.model.User;

public interface UserRepo {
    int registerUser(User user);

    User findByName(String name);

    User findByNameAndPassword(String name, String encordedPassword);
}
