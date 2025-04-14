package com.example;

import io.javalin.Javalin;

public class HelloWorld {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);

        // http://localhost:7000/ GET
        app.get("/", ctx -> {
            System.out.println("Hello, server!");
            ctx.result("Hello, user!");
        });

        // http://localhost:7000/dylan POST
        // You can't send the POST verb by visiting the URL with the browser,
        // so you must use something like Thunder Client.
        app.post("dylan", ctx -> {
            ctx.result("Hello, Dylan!");
        });
    }
}
