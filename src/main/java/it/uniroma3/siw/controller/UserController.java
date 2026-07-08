package it.uniroma3.siw.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.UserService;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "users/list";
    }

    @GetMapping("/users/{id}")
    public String show(@PathVariable Long id, Model model) {

        Optional<User> optional = userService.findUserById(id);

        if (optional.isEmpty())
            return "redirect:/users";

        model.addAttribute("user", optional.get());

        return "users/show";
    }
}