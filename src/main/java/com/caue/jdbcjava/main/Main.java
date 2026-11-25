package com.caue.jdbcjava.main;


import com.caue.jdbcjava.server.Server;
import com.caue.jdbcjava.service.UserService;
import com.sun.net.httpserver.HttpHandlers;
import com.sun.net.httpserver.Request;

import java.util.function.Predicate;

public class Main {
    static void main() {
        Predicate<Request> IS_POST = request -> request.getRequestMethod().equals("POST");
        HttpHandlers.

    }

}
