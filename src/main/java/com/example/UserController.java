package com.example;

import java.util.Objects;
import java.util.Optional;
import io.javalin.http.Handler;

public class UserController {
    public static Handler fetchAllUsernames = ctx -> {
        UserDao dao = UserDao.instance();
        Iterable<String> allUsers = dao.getAllUsernames();
        ctx.json(allUsers);
    };

    public static Handler fetchById = ctx -> {
        int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
        UserDao dao = UserDao.instance();
        Optional<User> user = dao.getUserById(id);

        // Return the user object in the response
        if (user.isPresent()) {
            ctx.json( user.get() );
        } else {
            ctx.html("Not Found");
        }

        /*
        Alternative to if-else statement above:
        user.ifPresent( u -> ctx.json(u) )

        if (!user.isPresent()) {
            ctx.html("Not Found");
        }
        */
    };
}
