package learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public int calcSum(String filepath) throws IOException {
        LineCallback<Integer> sumCallback = (line, value) -> value + Integer.parseInt(line);
        return this.lineReadTemplate(filepath, sumCallback, 0);
    }

    public int calcMultiply(String filepath) throws IOException {
        LineCallback<Integer> multiplyCallback = (line, value) -> value * Integer.parseInt(line);
        return this.lineReadTemplate(filepath, multiplyCallback, 1);
    }

    public String concatenate(String filepate) throws IOException {
        LineCallback<String> concatenateCallback = (line, value) -> value + line;
        return this.lineReadTemplate(filepate, concatenateCallback, "");
    }

    public <T> T lineReadTemplate(String filepath, LineCallback<T> callback, T initVal) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));
            T res = initVal;
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
