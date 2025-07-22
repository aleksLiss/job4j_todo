package ru.job4j.todo.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import java.util.List;

@Controller
@ThreadSafe
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @RequestMapping("/list/all")
    public String getAllTasks(Model model) {
        try {
            List<Task> allTasks = taskService.getAll();
            model.addAttribute("tasks", allTasks);
        } catch (Exception ex) {
            model.addAttribute("error", "Ошибка извлечения всех задач");
            return "errors/404";
        }
        return "tasks/allList";
    }

    @RequestMapping("/list/new")
    public String getNewTasks(Model model) {
        try {
            List<Task> newTasks = taskService.getNewTasks();
            model.addAttribute("tasks", newTasks);
        } catch (Exception ex) {
            model.addAttribute("error", "Ошибка извлечения новых задач");
            return "errors/404";
        }
        return "tasks/newList";
    }

    @RequestMapping("/list/done")
    public String getDoneTasks(Model model) {
        try {
            List<Task> doneTasks = taskService.getDoneTasks();
            model.addAttribute("tasks", doneTasks);
        } catch (Exception ex) {
            model.addAttribute("erorr", "Ошибка извлечения выполненных задач");
            return "errors/404";
        }
        return "tasks/doneList";
    }

    @RequestMapping("/*{id}")
    public String getTask() {
        try {
            // переделать что б возвращало Optional<Task>


        } catch (Exception ex) {

        }
        return "tasks/one";
    }




}
