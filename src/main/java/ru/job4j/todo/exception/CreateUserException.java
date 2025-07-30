package ru.job4j.todo.exception;

public class CreateUserException extends RuntimeException {
    public CreateUserException(String message) {
        super(message);
    }

    public CreateUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
