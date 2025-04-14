package com.example;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

public class HelloWorld {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);

        app.before(ctx -> {
            // runs before all requests
            System.out.println("I think somebody's about to do somethin'!");
        });

        // http://localhost:7000/ GET
        app.get("/", ctx -> {
            System.out.println("Hello, server!");
            ctx.result(
                "Hello, user!\n" + 
                "Status code: " + ctx.status().toString()
            );
        });

        // http://localhost:7000/lost GET
        app.get("/lost", ctx -> {
            System.out.println("I once was lost...");
            ctx.redirect("/found", HttpStatus.FOUND);
        });

        // http://localhost:7000/found GET
        app.get("/found", ctx -> {
            System.out.println("...but now I'm found.");
            ctx.result("Welcome to place.");
        });

        // http://localhost:7000/path/* GET
        app.get("/path/*", ctx -> { // will match anything starting with /path/
            ctx.result("You are here because " + ctx.path() + " matches " + ctx.matchedPath());
        });

        // http://localhost:7000/dylan POST
        // You can't send the POST verb by visiting the URL with the browser,
        // so you must use something like Thunder Client.
        app.post("/dylan", ctx -> {
            ctx.result(
                "Hello, Dylan!\n" +
                "Status code: " + ctx.status(404).toString() + "\n" +
                "jk lol"
            );

            // No idea what this actually does yet:
            ctx.status(201);
        });

        app.after(ctx -> {
            // run after all requests
            System.out.println("I was right, somebody did somethin'!");
        });
    }
}
