package org.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CachedCalculator implements InvocationHandler {

    private final Map<Integer, Integer> resultByArg = new HashMap<>();
    private Object target;

    public CachedCalculator(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Integer arg = (Integer) args[0];
        if (!resultByArg.containsKey(arg)) {
            System.out.println("key not found, new invocation");
            int result = (Integer) method.invoke(target, args);
            resultByArg.put(arg, result);
        }
        return resultByArg.get(arg);
    }
}
