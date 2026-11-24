package com.caue.jdbcjava.service;

import com.caue.jdbcjava.entity.User;
import com.caue.jdbcjava.repository.UserRepository;

import java.util.List;

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

    public static List<User> findAll(){
        return UserRepository.findAll();
    }

    public static User findById(Long id){
        return UserRepository.findById(id);
    }
}
