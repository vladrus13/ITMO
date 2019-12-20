package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.CommentCredentials;
import ru.itmo.wp.repository.CommentRepository;
import ru.itmo.wp.repository.PostRepository;
import ru.itmo.wp.repository.UserRepository;

import java.util.List;

@Service
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentService(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public void register(CommentCredentials commentCredentials, Post post, User user) {
        Comment comment = new Comment();
        comment.setText(commentCredentials.getText());
        comment.setUser(user);
        comment.setPost(post);
        post.addComment(comment);
        commentRepository.save(comment);
        postRepository.save(post);
        userRepository.save(user);
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }
}
