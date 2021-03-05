package io.github.oraindrop.service.test;

import io.github.oraindrop.dao.UserDao;
import io.github.oraindrop.domain.User;
import io.github.oraindrop.exception.TestUserServiceException;
import io.github.oraindrop.service.UserLevelUpgradePolicyDefault;

public class TestUserLevelUpgradePolicy extends UserLevelUpgradePolicyDefault {

    private String id;

    public TestUserLevelUpgradePolicy(UserDao userDao) {
        super(userDao);
        this.id = "id4";
    }

    @Override
    public void upgradeLevel(User user) {
        if (user.getId().equals(this.id)) {
            throw new TestUserServiceException();
        }

        super.upgradeLevel(user);
    }
}