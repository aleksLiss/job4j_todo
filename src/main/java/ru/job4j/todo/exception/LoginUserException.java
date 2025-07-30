package ru.job4j.todo.exception;

public class LoginUserException extends RuntimeException {
    public LoginUserException(String message) {
        super(message);
    }

    public LoginUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
