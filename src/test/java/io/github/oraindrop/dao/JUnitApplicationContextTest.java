package io.github.oraindrop.dao;

import io.github.oraindrop.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(classes = Application.class)
public class JUnitApplicationContextTest {

    @Autowired
    ApplicationContext context;

    static ApplicationContext contextObject = null;

    @Test
    public void test1() {
        Assertions.assertTrue(contextObject == null || contextObject == this.context);
        contextObject = this.context;
    }

    @Test
    public void test2() {
        Assertions.assertTrue(contextObject == null || contextObject == this.context);
        contextObject = this.context;
    }

    @Test
    public void test3() {
        Assertions.assertTrue(contextObject == null || contextObject == this.context);
        contextObject = this.context;
    }
}
