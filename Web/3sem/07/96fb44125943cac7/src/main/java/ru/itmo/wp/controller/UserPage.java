package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmo.wp.service.UserService;

@Controller
@RequestMapping("user")
public class UserPage extends Page {
    private final UserService userService;

    public UserPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id:\\d+}")
    public String user(@PathVariable long id, Model model) {
        model.addAttribute("info_user", userService.findById(id));
        return "UserPage";
    }
}
