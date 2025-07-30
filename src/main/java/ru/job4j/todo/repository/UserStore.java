package ru.job4j.todo.repository;

import ru.job4j.todo.model.User;

import java.util.Optional;

public interface UserStore {

    public User save(User user);

    public Optional<User> getUserByLogin(User user);
}
