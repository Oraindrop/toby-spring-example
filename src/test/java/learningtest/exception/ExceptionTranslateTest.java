package learningtest.exception;

import io.github.oraindrop.Application;
import io.github.oraindrop.dao.UserDao;
import io.github.oraindrop.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.hamcrest.HamcrestArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest(classes = Application.class)
public class ExceptionTranslateTest {

    @Autowired
    private UserDao dao;

    @Autowired
    private DataSource dataSource;

    private User user1;

    private User user2;

    private User user3;

    @BeforeEach
    public void setUp() {
        user1 = new User("zingo", "노징고", "1234");
        user2 = new User("choising", "최싱", "12345");
        user3 = new User("forever", "포에버", "123456");
    }

    @Test
    public void sqlExceptionTranslate() {
        dao.deleteAll();

        try {
            dao.add(user1);
            dao.add(user1);
        } catch (DuplicateKeyException e) {
            SQLException sqlException = (SQLException)e.getRootCause();
            SQLExceptionTranslator sqlExceptionTranslator = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);
            Assertions.assertTrue(sqlExceptionTranslator.translate(null, null, sqlException) instanceof DuplicateKeyException);
        }
    }


}