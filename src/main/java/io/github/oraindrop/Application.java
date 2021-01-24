package io.github.oraindrop;

import io.github.oraindrop.dao.CountingConnectionMaker;
import io.github.oraindrop.dao.WebConfiguration;
import io.github.oraindrop.dao.UserDao;
import io.github.oraindrop.domain.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        SpringApplication.run(Application.class, args);
        ApplicationContext context = new AnnotationConfigApplicationContext(WebConfiguration.class);
        UserDao dao = context.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("choising");
        user.setName("seungmin");
        user.setPassword("hello");

        dao.add(user);

        System.out.println(user.getId() + "reg success");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        if (!user.getName().equals(user2.getName())) {
            System.out.println("테스트 실패 (name)");
        } else if (!user.getPassword().equals(user2.getPassword())) {
            System.out.println("테스트 실패 (password)");
        } else {
            System.out.println("테스트 성공");
        }

        CountingConnectionMaker countingConnectionMaker = context.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println("Connection count : " + countingConnectionMaker.getCount());
    }
}
