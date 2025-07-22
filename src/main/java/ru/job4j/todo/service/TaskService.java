package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.List;

public interface TaskService {

    public Task save(Task task);

    public List<Task> getAll();

    public List<Task> getNewTasks();

    public List<Task> getDoneTasks();

    public void update(Task task);

    public void delete(Task task);
}
