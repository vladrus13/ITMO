package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.form.DisableForm;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UsersPage extends Page {
    private final UserService userService;

    public UsersPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("disableForm", new DisableForm());
        return "UsersPage";
    }

    @PostMapping("users/all")
    public String disable(@Valid @ModelAttribute("disableForm") DisableForm disableForm, HttpSession httpSession) {
        userService.setDisable(disableForm.getId(), !disableForm.getDisable());
        putMessage(httpSession, "Congrats, you make disable!");
        return "redirect:/users/all";
    }
}
