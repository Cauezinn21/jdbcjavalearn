package com.caue.jdbcjava.server;

import com.caue.jdbcjava.entity.User;
import com.caue.jdbcjava.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Server {
    static void main() throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);


        httpServer.createContext("/usersAll", exchange -> {
            try {
                if (!exchange.getRequestMethod().equals("GET")){
                    exchange.sendResponseHeaders(405, -1);
                }

                byte [] bytes;

                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                List<User> users = UserService.findAll();

                String string = objectMapper.writeValueAsString(users);

                bytes = string.getBytes(StandardCharsets.UTF_8);

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, bytes.length);

                try (OutputStream outputStream = exchange.getResponseBody()) {
                    outputStream.write(bytes);
                }
            } catch (Exception e) {
                throw new RuntimeException();
            }
            finally {
                exchange.close();
            }
        });

        httpServer.createContext("/users/", exchange -> {

            URI requestURI = exchange.getRequestURI();
            String[] split = requestURI.toString().split("/");


            if (split.length == 3){
               String string = split[2];
               long l = Long.parseLong(string);
               ObjectMapper objectMapper = new ObjectMapper();
               objectMapper.registerModule(new JavaTimeModule());
               objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

               byte [] bytes;
               User byId = UserService.findById(l);
               String string1 = objectMapper.writeValueAsString(byId);
               bytes = string1.getBytes(StandardCharsets.UTF_8);

               exchange.getResponseHeaders().set("Content-Type", "application/json");
               exchange.sendResponseHeaders(200, bytes.length);
                OutputStream responseBody = exchange.getResponseBody();
                responseBody.write(bytes);
                responseBody.close();


            }
            exchange.close();
        });

        httpServer.createContext("/users", exchange -> {
            try {
                if (!exchange.getRequestMethod().equals("POST")){
                    exchange.sendResponseHeaders(405, -1);
                }

                byte [] requestBytes;

                try (InputStream inputStream = exchange.getRequestBody()){
                    requestBytes = inputStream.readAllBytes();
                }
                ObjectMapper objectMapper = new ObjectMapper();

                User user = objectMapper.readValue(requestBytes, User.class);

                UserService.save(user);

                String response = "User created with sucessful!";
                byte[] bytes = response.getBytes(StandardCharsets.UTF_8);

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(201, bytes.length);

                try (OutputStream outputStream = exchange.getResponseBody()){
                    outputStream.write(bytes);

                }


            } catch (Exception e) {


                try {
                    String error = "Internal server error";
                    byte[] bytes = error.getBytes(StandardCharsets.UTF_8);

                    exchange.getResponseHeaders().set("Content-Type", "application/json");

                    exchange.sendResponseHeaders(500, bytes.length);

                    try (OutputStream responseBody = exchange.getResponseBody()){

                        responseBody.write(bytes);
                    }
                }


                catch (IOException f){
                    throw new RuntimeException(f);
                }
                throw new RuntimeException(e);
            }
            finally {
                exchange.close();
            }
        });
        httpServer.start();
    }
}
