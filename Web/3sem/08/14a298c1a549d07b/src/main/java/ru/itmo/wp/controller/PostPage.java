package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Controller
public class PostPage extends Page{
    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }

    @Guest
    @GetMapping("post/{id:\\d{1,10}}")
    public String postPageGet(@PathVariable long id, Model model, HttpSession httpSession) {
        if (postService.findById(id) == null) {
            putMessage(httpSession, "Post doesn't exist!");
            return "redirect:/";
        }
        model.addAttribute("commentForm", new Comment());
        model.addAttribute("postGet", postService.findById(id));
        model.addAttribute("isLogged", getUser(httpSession) != null);
        return "PostPage";
    }

    @Guest
    @GetMapping("post/{id:\\d{10,}}")
    public String postPageGetMore(@PathVariable String id, Model model, HttpSession httpSession) {
        return "AccessDeniedPage";
    }

    @PostMapping("post/{id:\\d{1,10}}")
    public String postPagePost(@PathVariable long id, @Valid @ModelAttribute("commentForm") Comment comment,
                               BindingResult bindingResult,
                               HttpSession httpSession, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("commentForm", comment);
            model.addAttribute("postGet", postService.findById(id));
            model.addAttribute("isLogged", getUser(httpSession) != null);
            return "PostPage";
        }
        if (postService.findById(id) == null) {
            putMessage(httpSession, "Post doesn't exist!");
            return "redirect:index/";
        }
        postService.writeComment(getUser(httpSession), postService.findById(id), comment);
        putMessage(httpSession, "You published new comment!");
        return "redirect:/post/" + id;
    }

    @PostMapping("post/{id:\\d{10,}}")
    public String postPagePostMore(@PathVariable String id, @Valid @ModelAttribute("post") Comment comment,
                               BindingResult bindingResult,
                               HttpSession httpSession) {
        return "AccessDeniedPage";
    }
}
