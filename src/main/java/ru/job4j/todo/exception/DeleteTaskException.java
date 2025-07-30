package ru.job4j.todo.exception;

public class DeleteTaskException extends RuntimeException {
    public DeleteTaskException(String message) {
        super(message);
    }

    public DeleteTaskException(String message, Throwable cause) {
        super(message, cause);
    }
}
