package learningtest.template;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CalcTest {

    Calculator calculator;
    String numFilePath;

    @BeforeEach
    public void setUp() {
        this.calculator = new Calculator();
        this.numFilePath = getClass().getResource("/numbers.txt").getPath();
    }

    @Test
    public void sumOfNumbers() throws IOException {
        assertEquals(calculator.calcSum(this.numFilePath), 10);
    }

    @Test
    public void multiplyOfNumbers() throws IOException {
        assertEquals(calculator.calcMultiply(this.numFilePath), 24);
    }

    @Test
    public void concatenateStrings() throws IOException {
        assertEquals(calculator.concatenate(this.numFilePath), "1234");
    }
}
