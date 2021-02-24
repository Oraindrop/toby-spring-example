package io.github.oraindrop.service;

import io.github.oraindrop.Application;
import io.github.oraindrop.dao.UserDao;
import io.github.oraindrop.domain.Level;
import io.github.oraindrop.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;

import static io.github.oraindrop.service.UserLevelUpgradePolicyDefault.MIN_LOGIN_COUNT_FOR_SILVER;
import static io.github.oraindrop.service.UserLevelUpgradePolicyDefault.MIN_RECOMMEND_FOR_GOLD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = Application.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TxProxyFactoryBean txProxyFactoryBean;

    private List<User> users;

    @BeforeEach
    public void setUp() {
        users = Arrays.asList(
                new User("id1", "name1", "p1", Level.BASIC, MIN_LOGIN_COUNT_FOR_SILVER - 1, 0, "email1"),
                new User("id2", "name2", "p2", Level.BASIC, MIN_LOGIN_COUNT_FOR_SILVER, 10, "email2"),
                new User("id3", "name3", "p3", Level.SILVER, 60 ,MIN_RECOMMEND_FOR_GOLD - 1, "email3"),
                new User("id4", "name4", "p4", Level.SILVER, 60 ,MIN_RECOMMEND_FOR_GOLD, "email4"),
                new User("id5", "name5", "p5", Level.GOLD, 100 ,100, "email5")
        );
    }

    @Test
    public void upgradeLevels() {
        UserDao mockUserDao = mock(UserDao.class);
        when(mockUserDao.getAll()).thenReturn(this.users);

        UserServiceImpl userServiceImpl = new UserServiceImpl(mockUserDao, new UserLevelUpgradePolicyDefault(mockUserDao));
        userServiceImpl.upgradeLevels();

        verify(mockUserDao, times(2)).update(any(User.class));
        verify(mockUserDao).update(users.get(1));
        assertEquals(users.get(1).getLevel(), Level.SILVER);
        verify(mockUserDao).update(users.get(3));
        assertEquals(users.get(3).getLevel(), Level.GOLD);
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

    private void checkLevelUpgraded(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());

        if (upgraded) {
            assertEquals(userUpdate.getLevel(), user.getLevel().nextLevel());
        } else {
            assertEquals(userUpdate.getLevel(), user.getLevel());
        }
    }

    @Test
    @DirtiesContext
    public void upgradeAllOrNothing() throws Exception {
        txProxyFactoryBean.setTarget(new UserServiceImpl(userDao, new UserLevelUpgradePolicyTest(userDao, users.get(3).getId())));
        UserService txUserService = (UserService) txProxyFactoryBean.getObject();

        userDao.deleteAll();

        for (User user : users) {
            userDao.add(user);
        }
        Assertions.assertThrows(TestUserServiceException.class, txUserService::upgradeLevels);

        checkLevelUpgraded(users.get(1), false);
    }

    static class UserLevelUpgradePolicyTest extends UserLevelUpgradePolicyDefault {

        private String id;

        public UserLevelUpgradePolicyTest(UserDao userDao, String id) {
            super(userDao);
            this.id = id;
        }

        @Override
        public void upgradeLevel(User user) {
            if (user.getId().equals(this.id)) {
                throw new TestUserServiceException();
            }

            super.upgradeLevel(user);
        }
    }

    static class TestUserServiceException extends RuntimeException {

    }

}