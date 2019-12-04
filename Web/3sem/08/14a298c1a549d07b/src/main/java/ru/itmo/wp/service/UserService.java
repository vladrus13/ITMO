package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.repository.PostRepository;
import ru.itmo.wp.repository.RoleRepository;
import ru.itmo.wp.repository.TagsRepository;
import ru.itmo.wp.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TagsRepository tagsRepository;
    private final PostRepository postRepository;

    /** @noinspection FieldCanBeLocal, unused */
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, TagsRepository tagsRepository, PostRepository postRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.tagsRepository = tagsRepository;
        this.postRepository = postRepository;
        this.roleRepository = roleRepository;
        for (Role.Name name : Role.Name.values()) {
            if (roleRepository.countByName(name) == 0) {
                roleRepository.save(new Role(name));
            }
        }
    }

    public User register(UserCredentials userCredentials) {
        User user = new User();
        user.setLogin(userCredentials.getLogin());
        userRepository.save(user);
        userRepository.updatePasswordSha(user.getId(), userCredentials.getLogin(), userCredentials.getPassword());
        return user;
    }

    public boolean isLoginVacant(String login) {
        return userRepository.countByLogin(login) == 0;
    }

    public User findByLoginAndPassword(String login, String password) {
        return login == null || password == null ? null : userRepository.findByLoginAndPassword(login, password);
    }

    public User findById(Long id) {
        return id == null ? null : userRepository.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAllByOrderByIdDesc();
    }

    public void writePost(User user, Post post) {
        List<Tag> tages = post.getTages();
        List<Tag> newTage = new ArrayList<>();
        for (Tag tag : tages) {
            if (tagsRepository.findByName(tag.getName()) == null) {
                tagsRepository.save(tag);
                newTage.add(tag);
            }
            else tag.setId(tagsRepository.findByName(tag.getName()).getId());
        }
        user.addPost(post, newTage);
        postRepository.save(post);
        userRepository.save(user);
    }

}
