package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskStore {

    public Task save(Task task);

    public List<Task> getAll();

    public List<Task> getNewTasks();

    public List<Task> getDoneTasks();

    public void update(Task task);

    public void delete(int id);

    public Optional<Task> getById(int taskId);
}
