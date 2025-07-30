package ru.job4j.todo.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@ThreadSafe
@RequestMapping("/users")
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create")
    public String getCreateUser() {
        return "users/create";
    }

    @PostMapping("/create")
    public String createUser(Model model, @ModelAttribute User user) {
        try {
            userService.save(user);
        } catch (Exception ex) {
            model.addAttribute("message", ex.getMessage());
            return "errors/404";
        }
        return "redirect:tasks/list/all";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "users/login";
    }

    @PostMapping("/login")
    public String getLoginPage(Model model, @ModelAttribute User user, HttpServletRequest request) {
        try {
            Optional<User> getSavedUser = userService.getUserByLogin(user);
            if (getSavedUser.isPresent()) {
                HttpSession session = request.getSession();
                session.setAttribute("user", getSavedUser.get());
            }
        } catch (Exception ex) {
            model.addAttribute("message", ex.getMessage());
            return "errors/404";
        }
        return "redirect:/tasks/list/all";
    }

    @GetMapping("/logout")
    public String getLoginPage(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }
}
