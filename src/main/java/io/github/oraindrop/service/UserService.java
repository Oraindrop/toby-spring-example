package io.github.oraindrop.service;

import io.github.oraindrop.domain.User;

public interface UserService {
    void add(User user);
    void upgradeLevels();
}
