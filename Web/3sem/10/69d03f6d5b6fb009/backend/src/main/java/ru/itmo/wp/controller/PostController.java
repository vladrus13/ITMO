package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.PostCredentials;
import ru.itmo.wp.form.UserRegisterCredentials;
import ru.itmo.wp.service.PostService;
import ru.itmo.wp.util.BindingResultUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class PostController extends ApiController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("posts")
    public void writePost(@RequestBody @Valid PostCredentials postCredentials, BindingResult bindingResult, HttpServletRequest HttpServletRequest) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BindingResultUtils.getErrorMessage(bindingResult));
        }
        if (getUser(HttpServletRequest) == null) {
            throw new ValidationException("Who are you?");
        }
        postService.register(postCredentials, getUser(HttpServletRequest));
    }

    @GetMapping("posts")
    public List<Post> findPosts() {
        return postService.findAll();
    }


}
