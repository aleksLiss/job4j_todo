package ru.job4j.todo.exception;

public class GetTaskByIdException extends RuntimeException {
    public GetTaskByIdException(String message) {
        super(message);
    }
}
