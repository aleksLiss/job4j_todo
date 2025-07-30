package ru.job4j.todo.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserStore;

import java.util.Optional;

@ThreadSafe
@Service
public class HbmUserService implements UserService {

    private final UserStore userRepository;

    public HbmUserService(UserStore userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByLogin(User user) {
        return userRepository.getUserByLogin(user);
    }
}
