package io.github.oraindrop.service;

import io.github.oraindrop.domain.User;

import java.util.List;

public interface UserService {
    void add(User user);
    User get(String id);
    List<User> getAll();
    void deleteAll();
    void update(User user);
    void upgradeLevels();
}
