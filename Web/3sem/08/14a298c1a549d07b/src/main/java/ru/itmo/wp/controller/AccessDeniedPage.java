package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessDeniedPage extends Page {
    @GetMapping("/accessDenied")
    public String index() {
        return "AccessDeniedPage";
    }
}
