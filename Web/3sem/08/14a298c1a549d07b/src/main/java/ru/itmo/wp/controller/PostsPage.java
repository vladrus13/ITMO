package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostsPage extends Page {
    @GetMapping("/posts")
    public String posts() {
        return "PostsPage";
    }
}
