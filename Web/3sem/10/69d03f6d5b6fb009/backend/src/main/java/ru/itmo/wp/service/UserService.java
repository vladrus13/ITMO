package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.UserEnterCredentials;
import ru.itmo.wp.form.UserRegisterCredentials;
import ru.itmo.wp.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByLoginAndPassword(String login, String password) {
        return login == null || password == null ? null : userRepository.findByLoginAndPassword(login, password);
    }

    public void register(UserRegisterCredentials userRegisterCredentials) {
        User user = new User();
        user.setLogin(userRegisterCredentials.getLogin());
        user.setAdmin(false);
        user.setName(userRegisterCredentials.getName());
        userRepository.save(user);
        userRepository.updatePasswordSha(user.getId(), userRegisterCredentials.getLogin(), userRegisterCredentials.getPassword());
    }

    public boolean isLoginVacant(String login) {
        return userRepository.countByLogin(login) == 0;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
