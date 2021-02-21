package learningtest.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UpercaseHandler implements InvocationHandler {

    Object target;

    public UpercaseHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object ret = method.invoke(target, args);
        if (ret instanceof  String && method.getName().startsWith("say")) {
            return ((String)ret).toUpperCase();
        } else {
            return ret;
        }
    }
}
