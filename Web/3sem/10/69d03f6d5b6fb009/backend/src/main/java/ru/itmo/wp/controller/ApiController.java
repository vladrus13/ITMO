package ru.itmo.wp.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import ru.itmo.wp.domain.User;

import javax.servlet.http.HttpServletRequest;

public abstract class ApiController {
    @ModelAttribute
    public User getUser(HttpServletRequest httpServletRequest) {
        return (User) httpServletRequest.getAttribute("user");
    }
}
