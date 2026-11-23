package com.caue.jdbcjava.repository;

import com.caue.jdbcjava.entity.User;
import com.caue.jdbcjava.factory.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRepository {

    public static void save(User user){
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("Insert Into users(name) values (?)")){
             preparedStatement.setString(1, user.getName());
             preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
