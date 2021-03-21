package io.github.oraindrop.service.test;

import io.github.oraindrop.dao.UserDao;
import io.github.oraindrop.domain.User;
import io.github.oraindrop.service.user.UserLevelUpgradePolicy;
import io.github.oraindrop.service.user.UserServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TestUserService extends UserServiceImpl {

    public TestUserService(UserDao userDao, UserLevelUpgradePolicy userLevelUpgradePolicy) {
        super(userDao, userLevelUpgradePolicy);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAll() {
        System.out.println(super.getAll());
        for (User user : super.getAll()) {
            super.getUserDao().update(user);
        }
        return null;
    }
}
