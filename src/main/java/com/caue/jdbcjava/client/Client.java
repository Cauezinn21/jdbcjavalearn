package com.caue.jdbcjava.client;

import com.caue.jdbcjava.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {
    static void main() throws IOException, InterruptedException {
        HttpClient build = HttpClient.newBuilder().build();

        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User(null, "Pablo", null);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(user))).uri(URI.create("http://localhost:8080/users")).build();

//        HttpResponse.BodyHandler<String> stringBodyHandler = HttpResponse.BodyHandlers.ofString();

        HttpResponse<String> send = build.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println(send.body());

        build.close();
    }
}
