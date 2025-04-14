package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserDao {
    private List<User> users = new ArrayList<>(Arrays.asList(
      new User(0, "Steve Rogers"),
      new User(1, "Tony Stark"),
      new User(2, "Carol Danvers")
    ));

    private static UserDao userDao = null;

    private UserDao() {
    }

    static UserDao instance() {
        if (userDao == null) {
            userDao = new UserDao();
        }
        return userDao;
    }

    Optional<User> getUserById(int id) {
        return users.stream()
          .filter(u -> u.id == id)
          .findAny();
    }

    Iterable<String> getAllUsernames() {
        return users.stream()
          .map(user -> user.name)
          .collect(Collectors.toList());
    }

    void addUser(User user) {
        users.add(user);
    }
}
