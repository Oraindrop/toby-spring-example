package learningtest.junit;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class JUnitTest {

    static Set<JUnitTest> testObjects = new HashSet<>();

    @Test
    public void test1() {
        assertFalse(testObjects.contains(this));
        testObjects.add(this);
    }

    @Test
    public void test2() {
        assertFalse(testObjects.contains(this));
        testObjects.add(this);
    }

    @Test
    public void test3() {
        assertFalse(testObjects.contains(this));
        testObjects.add(this);
    }

}
