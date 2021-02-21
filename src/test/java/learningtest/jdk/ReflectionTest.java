package learningtest.jdk;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReflectionTest {

    @Test
    public void invokeMethod() throws Exception {
        String name = "Spring";

        assertEquals(name.length(), 6);

        Method lengthMethod = String.class.getMethod("length");
        assertEquals((Integer)lengthMethod.invoke(name), 6);

        assertEquals(name.charAt(0), 'S');

        Method charAtMethod = String.class.getMethod("charAt", int.class);
        assertEquals((Character)charAtMethod.invoke(name, 0), 'S');
    }

    @Test
    public void simpleProxy() {
        Hello hello = new HelloTarget();
        assertEquals(hello.sayHello("Toby"), "Hello Toby");
        assertEquals(hello.sayHi("Toby"), "Hi Toby");
        assertEquals(hello.sayThankYou("Toby"), "Thank You Toby");

        Hello proxiedHello = (Hello) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {
                Hello.class
        }, new UppercaseHandler(new HelloTarget()));

        assertEquals(proxiedHello.sayHello("Toby"), "HELLO TOBY");
        assertEquals(proxiedHello.sayHi("Toby"), "HI TOBY");
        assertEquals(proxiedHello.sayThankYou("Toby"), "THANK YOU TOBY");
    }

}
