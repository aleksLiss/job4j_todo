package ru.job4j.todo.advice;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.job4j.todo.exception.*;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<UnexpectedException> handleUnexceptedException(UnexpectedException ex) {
        LOGGER.error("Unexpected error: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new UnexpectedException("Internal server error"));
    }

    @ExceptionHandler(GetTaskByIdException.class)
    public ResponseEntity<GetTaskByIdException> handleGetTaskByIdException(GetTaskByIdException ex) {
        LOGGER.error("Error get task by id {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new GetTaskByIdException("Error get task by id"));
    }

    @ExceptionHandler(SaveTaskException.class)
    public ResponseEntity<SaveTaskException> handleSaveTaskException(SaveTaskException ex) {
        LOGGER.error("Error save task {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new SaveTaskException("Error save task"));
    }

    @ExceptionHandler(UpdateTaskException.class)
    public ResponseEntity<UpdateTaskException> handleUpdateTaskException(UpdateTaskException ex) {
        LOGGER.error("Error update task {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new UpdateTaskException("Error update task"));
    }

    @ExceptionHandler(CreateUserException.class)
    public ResponseEntity<CreateUserException> handleCreateUserException(CreateUserException ex) {
        LOGGER.error("Error register user {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new CreateUserException("Error register user"));
    }

    @ExceptionHandler(LoginUserException.class)
    public ResponseEntity<LoginUserException> handleLoginUserException(LoginUserException ex) {
        LOGGER.error("Error login user {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new LoginUserException("Error login user"));
    }
}