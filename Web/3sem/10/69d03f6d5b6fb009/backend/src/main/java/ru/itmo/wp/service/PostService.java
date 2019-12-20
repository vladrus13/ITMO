package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.PostCredentials;
import ru.itmo.wp.repository.PostRepository;
import ru.itmo.wp.repository.UserRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post findById(long id) {
        return postRepository.findById(id).orElse(null);
    }

    public void register(PostCredentials postCredentials, User user) {
        Post post = new Post();
        post.setTitle(postCredentials.getTitle());
        post.setText(postCredentials.getText());
        user.addPost(post);
        postRepository.save(post);
        userRepository.save(user);
    }

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc();
    }
}
