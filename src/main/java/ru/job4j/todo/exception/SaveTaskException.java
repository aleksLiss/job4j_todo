package ru.job4j.todo.exception;

public class SaveTaskException extends RuntimeException {
    public SaveTaskException(String message) {
        super(message);
    }

  public SaveTaskException(String message, Throwable cause) {
    super(message, cause);
  }
}
