package com.caue.jdbcjava.repository;

import com.caue.jdbcjava.entity.User;
import com.caue.jdbcjava.factory.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public static List<User> findAll(){
        List<User> users = new ArrayList<>();
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("Select * from users")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            try (resultSet){
               while (resultSet.next()){
                   users.add(new User(resultSet.getLong("id"),
                           resultSet.getString("name"),
                           resultSet.getObject("created_at", LocalDateTime.class)));
               }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static User findById(Long id){
        User user = null;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, created_at FROM users WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            try (resultSet) {
                if (resultSet.next()) {
                    user = new User(resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getObject("created_at", LocalDateTime.class));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


}
