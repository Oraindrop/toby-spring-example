package io.github.oraindrop.service;

import io.github.oraindrop.domain.User;

public interface UserLevelUpgradePolicy {

    boolean canUpgradeLevel(User user);

    void upgradeLevel(User user);
}
