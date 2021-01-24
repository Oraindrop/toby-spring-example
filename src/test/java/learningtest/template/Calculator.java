package learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public int calcSum(String filepath) throws IOException {
        LineCallback sumCallback = (line, value) -> value + Integer.parseInt(line);
        return this.lineReadTemplate(filepath, sumCallback, 0);
    }

    public int calcMultiply(String filepath) throws IOException {
        LineCallback multiplyCallback = (line, value) -> value * Integer.parseInt(line);
        return this.lineReadTemplate(filepath, multiplyCallback, 1);
    }

    public int lineReadTemplate(String filepath, LineCallback callback, int initVal) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));
            int res = initVal;
            String line = null;

            while ((line = br.readLine()) != null) {
                res = callback.doSomethingWithLine(line, res);
            }
            return res;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

}
