package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.User;

import java.util.List;

public interface UserRepository {
    User find(long id);
    User findByLogin(String login);
    User findByEmail(String email);
    User findByXAndPasswordSha(String login, String passwordSha);
    List<User> findAll();
    void save(User user, String passwordSha, String email);
    User findByEmailAndPasswordSha(String email, String passwordSha);
    long findCount();
}
