package com.caue.jdbcjava.server;

import com.caue.jdbcjava.entity.User;
import com.caue.jdbcjava.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class Server {
    static void main() throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);

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
                e.printStackTrace();

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
                    f.printStackTrace();
                }
            }
            finally {
                exchange.close();
            }
        });
        httpServer.start();
    }
}
