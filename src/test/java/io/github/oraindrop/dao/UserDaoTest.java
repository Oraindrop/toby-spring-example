package io.github.oraindrop.dao;

import io.github.oraindrop.Application;
import io.github.oraindrop.domain.Level;
import io.github.oraindrop.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = Application.class)
public class UserDaoTest {

    @Autowired
    private UserDao dao;

    private User user1;

    private User user2;

    private User user3;

    @BeforeEach
    public void setUp() {
        user1 = new User("zingo", "노징고", "1234", Level.BASIC, 1, 0);
        user2 = new User("choising", "최싱", "12345", Level.SILVER, 55, 10);
        user3 = new User("forever", "포에버", "123456", Level.GOLD, 100 ,40);
    }

    private void checkSameUser(User user1, User user2) {
        assertEquals(user1.getId(), user2.getId());
        assertEquals(user1.getName(), user2.getName());
        assertEquals(user1.getPassword(), user2.getPassword());
        assertEquals(user1.getLevel(), user2.getLevel());
        assertEquals(user1.getLogin(), user2.getLogin());
        assertEquals(user1.getRecommend(), user2.getRecommend());
    }

    @Test
    public void addAndGet() {
        dao.deleteAll();
        assertEquals(dao.getCount(), 0);

        dao.add(user1);
        dao.add(user2);
        assertEquals(dao.getCount(), 2);

        User userGet1 = dao.get(user1.getId());
        checkSameUser(user1, userGet1);

        User userGet2 = dao.get(user2.getId());
        checkSameUser(user2, userGet2);
    }

    @Test
    public void count() {
        dao.deleteAll();
        assertEquals(dao.getCount(), 0);

        dao.add(user1);
        assertEquals(dao.getCount(), 1);

        dao.add(user2);
        assertEquals(dao.getCount(), 2);

        dao.add(user3);
        assertEquals(dao.getCount(), 3);
    }

    @Test
    public void getUserFailure() {
        dao.deleteAll();
        assertEquals(dao.getCount(), 0);

        assertThrows(EmptyResultDataAccessException.class, () -> {
            dao.get("unkown_id");
        });
    }

    @Test
    public void duplicateKey() {
        dao.deleteAll();

        dao.add(user1);
        assertThrows(DuplicateKeyException.class, () -> {
            dao.add(user1);
        });
    }

    @Test
    public void update() {
        dao.deleteAll();

        dao.add(user1);
        dao.add(user2);

        user1.setName("노진산");
        user1.setPassword("5678");
        user1.setLevel(Level.GOLD);
        user1.setLogin(1000);
        user1.setRecommend(999);

        dao.update(user1);

        User updateUser1 = dao.get(user1.getId());
        checkSameUser(user1, updateUser1);

        User userSame2 = dao.get(user2.getId());
        checkSameUser(user2, userSame2);
    }
}