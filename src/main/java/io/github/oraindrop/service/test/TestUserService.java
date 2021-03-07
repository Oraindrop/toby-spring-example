package io.github.oraindrop.service.test;

import io.github.oraindrop.dao.UserDao;
import io.github.oraindrop.domain.User;
import io.github.oraindrop.service.UserLevelUpgradePolicy;
import io.github.oraindrop.service.UserServiceImpl;

import java.util.List;

public class TestUserService extends UserServiceImpl {

    public TestUserService(UserDao userDao, UserLevelUpgradePolicy userLevelUpgradePolicy) {
        super(userDao, userLevelUpgradePolicy);
    }

    @Override
    public List<User> getAll() {
        for (User user : super.getAll()) {
            super.update(user);
        }
        return null;
    }
}
