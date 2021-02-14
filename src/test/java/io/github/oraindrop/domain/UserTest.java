package io.github.oraindrop.domain;

import io.github.oraindrop.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Application.class)
class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void upgradeLevel() {
        Level[] levels = Level.values();
        for (Level level : levels) {
            if (level.nextLevel() == null) continue;
            user.setLevel(level);
            user.upgradeLevel();
            assertEquals(user.getLevel(), level.nextLevel());
        }
    }

    @Test
    public void cannotUpgradeLevel() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Level[] levels = Level.values();
            for (Level level : levels) {
                if (level.nextLevel() != null) continue;
                user.setLevel(level);
                user.upgradeLevel();
            }
        });
    }
}