package org.example;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        CalculatorImpl calculator = new CalculatorImpl();
        // вывести все методы класса
        System.out.println("methods:");
        System.out.println(getAllMethods(calculator));

        // вывести все геттеры
        System.out.println("\ngetters:");
        System.out.println(getAllGetters(calculator));

        // проверить, что все String константы имеют значение = их имени
        System.out.println("\nString константы со значением = их имени:");
        System.out.println(checkAllConstantsNames(calculator));

        // proxy
        System.out.println("кэширующий прокси:");
        ClassLoader cl = CalculatorImpl.class.getClassLoader();
        Calculator proxy = (Calculator) Proxy.newProxyInstance(cl,
                new Class[] {Calculator.class}, new CachedCalculator(calculator));
        System.out.println(proxy.calc(4));
        System.out.println(proxy.calc(5));
        System.out.println(proxy.calc(4));

        // performanceProxy
        System.out.println("\nperformanceProxy");
        Calculator performanceProxy = (Calculator) Proxy.newProxyInstance(cl,
                new Class[] {Calculator.class}, new PerformanceProxy(calculator));
        System.out.println("c @Metric:");
        System.out.println(performanceProxy.calc(3));
        System.out.println("без аннотации:");
        System.out.println(performanceProxy.calcWithoutAnnotation(3));

        // номер 7
        System.out.println("\nзадание 7:");
        CalculatorImpl from = new CalculatorImpl();
        CalculatorImpl to = new CalculatorImpl();
        from.setSomeVariable(123);
        System.out.println(to.getSomeVariable());
        BeanUtils.assign(to, from);
        System.out.println(to.getSomeVariable());
    }

    public static List<String> getAllMethods(Object obj) {
        Method[] methods = obj.getClass().getMethods();
        return Arrays.stream(methods).map(Method::getName).collect(Collectors.toList());
    }

    public static List<String> getAllGetters(Object obj) {
        Method[] methods = obj.getClass().getMethods();
        return Arrays.stream(methods).filter(Main::isGetter).map(Method::getName).collect(Collectors.toList());
    }

    private static boolean isGetter(Method method){
        return method.getName().startsWith("get") && method.getParameterTypes().length == 0 &&
                !void.class.equals(method.getReturnType());
    }

    public static List<String> checkAllConstantsNames(Object obj) {
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        List<String> constantNamesThatAreEqual = new ArrayList<>();
        try {
            for (Field field : declaredFields) {
                field.setAccessible(true);
                if (field.getType().getName().equals("java.lang.String") && field.getName().equals(field.get(obj))) {
                    constantNamesThatAreEqual.add(field.get(obj).toString());
                }
            }
        } catch (IllegalAccessException exception) {
            System.out.println(exception);
        }
        return constantNamesThatAreEqual;
    }
}