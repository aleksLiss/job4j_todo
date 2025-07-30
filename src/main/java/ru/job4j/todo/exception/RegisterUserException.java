package ru.job4j.todo.exception;

public class RegisterUserException extends RuntimeException {
    public RegisterUserException(String message) {
        super(message);
    }
}
