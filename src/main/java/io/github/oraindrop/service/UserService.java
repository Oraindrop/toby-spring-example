package io.github.oraindrop.service;

import io.github.oraindrop.dao.UserDao;
import io.github.oraindrop.domain.Level;
import io.github.oraindrop.domain.User;

import java.util.List;

public class UserService {

    private UserDao userDao;

    private UserLevelUpgradePolicy userLevelUpgradePolicy;

    public UserService(UserDao userDao, UserLevelUpgradePolicy userLevelUpgradePolicy) {
        this.userDao = userDao;
        this.userLevelUpgradePolicy = userLevelUpgradePolicy;
    }

    public void upgradeLevels() {
        List<User> users = userDao.getAll();

        for (User user : users) {
            if (userLevelUpgradePolicy.canUpgradeLevel(user)) {
                userLevelUpgradePolicy.upgradeLevel(user);
            }
        }
    }

    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }

        userDao.add(user);
    }
}
