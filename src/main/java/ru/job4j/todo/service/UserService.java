package ru.job4j.todo.service;

import ru.job4j.todo.model.User;

import java.util.Optional;

public interface UserService {
    public User save(User user);

    public Optional<User> getUserByLogin(User user);
}
