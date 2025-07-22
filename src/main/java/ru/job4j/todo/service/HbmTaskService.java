package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.Store;

import java.util.List;

@Service
@AllArgsConstructor
@ThreadSafe
public class HbmTaskService implements TaskService {

    private final Store taskRepository;

    @Override
    public Task save(Task task) {
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
    public void delete(Task task) {
        taskRepository.delete(task);
    }
}
