package io.github.oraindrop.service;

import io.github.oraindrop.Application;
import io.github.oraindrop.dao.UserDao;
import io.github.oraindrop.domain.Level;
import io.github.oraindrop.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static io.github.oraindrop.service.UserLevelUpgradePolicyDefault.MIN_LOGIN_COUNT_FOR_SILVER;
import static io.github.oraindrop.service.UserLevelUpgradePolicyDefault.MIN_RECOMMEND_FOR_GOLD;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    private List<User> users;

    @BeforeEach
    public void setUp() {
        users = Arrays.asList(
                new User("id1", "name1", "p1", Level.BASIC, MIN_LOGIN_COUNT_FOR_SILVER - 1, 0),
                new User("id2", "name2", "p2", Level.BASIC, MIN_LOGIN_COUNT_FOR_SILVER, 10),
                new User("id3", "name3", "p3", Level.SILVER, 60 ,MIN_RECOMMEND_FOR_GOLD - 1),
                new User("id4", "name4", "p4", Level.SILVER, 60 ,MIN_RECOMMEND_FOR_GOLD),
                new User("id5", "name5", "p5", Level.GOLD, 100 ,100)
        );
    }

    @Test
    public void upgradeLevels() {
        userDao.deleteAll();

        for (User user : users) {
            userDao.add(user);
        }

        userService.upgradeLevels();

        checkLevel(users.get(0), false);
        checkLevel(users.get(1), true);
        checkLevel(users.get(2), false);
        checkLevel(users.get(3), true);
        checkLevel(users.get(4), false);
    }

    @Test
    public void add() {
        userDao.deleteAll();

        User userWithLevel = users.get(4); // gold
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertEquals(userWithLevelRead.getLevel(), userWithLevel.getLevel());
        assertEquals(userWithoutLevelRead.getLevel(), Level.BASIC);
    }

    private void checkLevel(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());

        if (upgraded) {
            assertEquals(userUpdate.getLevel(), user.getLevel().nextLevel());
        } else {
            assertEquals(userUpdate.getLevel(), user.getLevel());
        }

    }
}