package org.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class PerformanceProxy implements InvocationHandler {

    private Object target;

    public PerformanceProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(org.example.Metric.class)) {
            long startTime = System.nanoTime();
            int result = (Integer) method.invoke(target, args);
            System.out.println("Время работы метода: " + (System.nanoTime() - startTime) + " (в наносек)");
            return result;
        } else {
            return method.invoke(target, args);
        }
    }
}
