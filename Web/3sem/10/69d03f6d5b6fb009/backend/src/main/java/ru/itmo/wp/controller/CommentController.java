package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.CommentCredentials;
import ru.itmo.wp.service.CommentService;
import ru.itmo.wp.service.PostService;
import ru.itmo.wp.util.BindingResultUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class CommentController extends ApiController {

    private final CommentService commentService;
    private final PostService postService;

    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    @PostMapping("comment")
    public void writePost(@RequestBody @Valid CommentCredentials commentCredentials, BindingResult bindingResult, HttpServletRequest HttpServletRequest) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BindingResultUtils.getErrorMessage(bindingResult));
        }
        commentService.register(commentCredentials, postService.findById(Long.parseLong(commentCredentials.getPostId())), getUser(HttpServletRequest));
    }

    @GetMapping("comment")
    public List<Comment> findAll() {return commentService.findAll(); }
}
