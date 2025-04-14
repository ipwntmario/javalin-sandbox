package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);

        app.before(ctx -> {
            // runs before all requests
            System.out.println("I think somebody's about to do somethin'!");
        });

        // http://localhost:7000/ GET
        app.get("/helloworld", ctx -> {
            System.out.println("Hello, server!");
            ctx.result(
                "Hello, user!\n" + 
                "Status code: " + ctx.status().toString()
            );
        });

        /*
         * Fetch all users.
         */
        // http://localhost:7000/users GET
        app.get("/users", UserController.fetchAllUsernames);

        /*
         * Fetch a single user by id.
         */
        // http://localhost:7000/users/{id} GET
        app.get("/users/{id}", UserController.fetchById);

        /*
         * Create a new user.
         */
        // http://localhost:7000/ POST
        app.post("/",  ctx -> {
            User user = ctx.bodyAsClass(User.class);
            UserDao dao = UserDao.instance();
            dao.addUser(user);
            ctx.status(201); // Created
            ctx.json(user);
        });

        // http://localhost:7000/lost GET
        app.get("/lost", ctx -> {
            System.out.println("I once was lost...");
            ctx.redirect("/found", HttpStatus.FOUND);
        });

        // http://localhost:7000/found GET
        app.get("/found", ctx -> {
            System.out.println("...but now I'm found.");
            ctx.result("Welcome to here. ⫷h⫸⫷e⫸⫷l⫸⫷l⫸⫷o⫸");
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

        // TODO: figure this out later.
        app.error(404, null);

        // TODO: figure this out later.
        app.exception(Exception.class, null);

        logger.info("Example log from {}", App.class.getSimpleName());
    }
}
