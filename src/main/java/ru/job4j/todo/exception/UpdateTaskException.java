package ru.job4j.todo.exception;

public class UpdateTaskException extends RuntimeException {
    public UpdateTaskException(String message) {
        super(message);
    }

    public UpdateTaskException(String message, Throwable cause) {
        super(message, cause);
    }
}
