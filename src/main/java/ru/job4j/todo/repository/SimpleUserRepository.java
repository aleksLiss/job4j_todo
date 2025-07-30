package ru.job4j.todo.repository;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.exception.LoginUserException;
import ru.job4j.todo.exception.RegisterUserException;
import ru.job4j.todo.model.User;

import java.util.Optional;

@Repository
@ThreadSafe
public class SimpleUserRepository implements UserStore {

    private final SessionFactory sessionFactory;

    public SimpleUserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User save(User user) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw new RegisterUserException("Ошибка регистрации: такой пользователь уже существует");
        } finally {
            session.close();
        }
        return user;
    }

    @Override
    public Optional<User> getUserByLogin(User user) {
        Session session = sessionFactory.openSession();
        Optional<User> foundUserByLogin;
        try {
            session.beginTransaction();
            foundUserByLogin = Optional.ofNullable(session.createQuery("FROM User WHERE login = :uLogin", User.class)
                    .setParameter("uLogin", user.getLogin())
                    .getSingleResult());
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new LoginUserException("Ошибка входа: проверьте вводимые данные");
        } finally {
            session.close();
        }
        return foundUserByLogin;
    }
}
