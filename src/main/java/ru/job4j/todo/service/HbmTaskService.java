package ru.job4j.todo.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskStore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@ThreadSafe
public class HbmTaskService implements TaskService {

    private final TaskStore taskRepository;

    public HbmTaskService(TaskStore taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task save(Task task) {
        task.setCreated(LocalDateTime.now());
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getAll() {
        return taskRepository.getAll();
    }

    @Override
    public List<Task> getNewTasks() {
        return taskRepository.getNewTasks();
    }

    @Override
    public List<Task> getDoneTasks() {
        return taskRepository.getDoneTasks();
    }

    @Override
    public void update(Task task) {
        taskRepository.update(task);
    }

    @Override
    public void delete(int id) {
        taskRepository.delete(id);
    }

    @Override
    public Optional<Task> getById(int taskId) {
        return taskRepository.getById(taskId);
    }
}
