package com.caue.jdbcjava.service;

import com.caue.jdbcjava.entity.User;
import com.caue.jdbcjava.repository.UserRepository;

public class UserService {
    private UserService(){

    }

    public static void save (User user){
        if (user == null){
            throw new IllegalArgumentException("The user cannot be null");
        }
        if (user.getName() == null || user.getName().isBlank()){
            throw new IllegalArgumentException("User name is required");
        }
        UserRepository.save(user);
    }
}
