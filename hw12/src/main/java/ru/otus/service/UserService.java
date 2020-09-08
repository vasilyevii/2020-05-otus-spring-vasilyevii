package ru.otus.service;

import ru.otus.model.User;

import java.util.Optional;

public interface UserService {

    User saveUser(User user);
    Optional<User> findUserByName(String name);

}
