package ru.job4j.todo.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@RequestMapping("/tasks")
@Controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/list/all")
    public String getAllTasks(Model model) {
        List<Task> allTasks = taskService.getAll();
        try {
            model.addAttribute("tasks", allTasks);
            return "tasks/allList";
        } catch (Exception ex) {
            model.addAttribute("message", "Ошибка извлечения всех задач");
            return "errors/404";
        }
    }

    @GetMapping("/list/new")
    public String getNewTasks(Model model) {
        try {
            List<Task> newTasks = taskService.getNewTasks();
            model.addAttribute("tasks", newTasks);
        } catch (Exception ex) {
            model.addAttribute("message", "Ошибка извлечения новых задач");
            return "errors/404";
        }
        return "tasks/newList";
    }

    @GetMapping("/list/done")
    public String getDoneTasks(Model model) {
        try {
            List<Task> doneTasks = taskService.getDoneTasks();
            model.addAttribute("tasks", doneTasks);
        } catch (Exception ex) {
            model.addAttribute("message", "Ошибка извлечения выполненных задач");
            return "errors/404";
        }
        return "tasks/doneList";
    }

    @GetMapping("/{id}")
    public String getOneTaskById(Model model, @PathVariable int id) {
        try {
            Optional<Task> foundTask = taskService.getById(id);
            foundTask.ifPresent(task -> model.addAttribute("task", task));
        } catch (Exception ex) {
            model.addAttribute("message", "Ошибка извлечения задачи");
            return "errors/404";
        }
        return "tasks/one";
    }

    @PostMapping("/update")
    public String updateTask(Model model, @ModelAttribute Task task) {
        try {
            taskService.update(task);
        } catch (Exception ex) {
            model.addAttribute("message", "Ошибка обновления задачи");
            return "errors/404";
        }
        return "redirect:/tasks/list/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(Model model, @PathVariable int id) {
        try {
            taskService.delete(id);
        } catch (Exception ex) {
            model.addAttribute("message", "Ошибка удаления задачи");
            return "errors/404";
        }
        return "redirect:/tasks/list/all";
    }

    @PostMapping("/create")
    public String createTask(Model model, @ModelAttribute Task task) {
        try {
            taskService.save(task);
        } catch (Exception ex) {
            model.addAttribute("message", "Ошибка создания задачи");
            return "errors/404";
        }
        return "redirect:/tasks/list/all";
    }

    @GetMapping("/create")
    public String getCreateTask(@ModelAttribute Task task) {
        return "tasks/create";
    }

    @GetMapping("/update/{id}")
    public String getUpdateTask(Model model, @PathVariable int id) {
        try {
            Optional<Task> foundTask = taskService.getById(id);
            if (foundTask.isPresent()) {
                model.addAttribute("task", foundTask.get());
            }
        } catch (Exception ex) {
            model.addAttribute("message", "Ошибка редактирования задачи");
            return "errors/404";
        }
        return "tasks/update";
    }
}
