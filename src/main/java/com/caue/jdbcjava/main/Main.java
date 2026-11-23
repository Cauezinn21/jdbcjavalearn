package com.caue.jdbcjava.main;

import com.caue.jdbcjava.entity.User;
import com.caue.jdbcjava.service.UserService;

public class Main {
    static void main() {
        User userOne = User.builder().name("Cauê").build();
        UserService.save(userOne);

    }
}
