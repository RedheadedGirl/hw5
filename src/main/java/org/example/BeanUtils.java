package org.example;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BeanUtils {
    /**
     * Scans object "from" for all getters. If object "to"
     * contains correspondent setter, it will invoke it
     * to set property value for "to" which equals to the property
     * of "from".
     * <p/>
     * The type in setter should be compatible to the value returned
     * by getter (if not, no invocation performed).
     * Compatible means that parameter type in setter should
     * be the same or be superclass of the return type of the getter.
     * <p/>
     * The method takes care only about public methods.
     *
     * @param to   Object which properties will be set.
     * @param from Object which properties will be used to get values.
     */
    public static void assign(Object to, Object from) {
        List<Method> allGetters = getAllGetters(from);
        System.out.println(allGetters);
        List<Method> allSetters = getAllSetters(to);
        System.out.println(allSetters);
        for (Method getter: allGetters) {
            String getterReturnType = getter.getReturnType().getName();
            List<Method> list = allSetters.stream().filter(setterMethod -> {
                Class<?> parameterType = setterMethod.getParameterTypes()[0];
                String setterName = setterMethod.getName().replaceFirst("set", "");
                String getterName = getter.getName().replaceFirst("get", "");
                return parameterType.getName().equals(getterReturnType) && setterName.equals(getterName); // или суперклассу
            }).toList();
            if (list.isEmpty()) {
                return;
            }
            try {
//                getter.getReturnType();
                Object invoke = getter.invoke(from);
                Method setter = list.get(0);
                setter.invoke(to, invoke);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                System.out.println("error while get/set");
            }

        }

    }


    public static List<Method> getAllGetters(Object obj) {
        Method[] methods = obj.getClass().getMethods();
        return Arrays.stream(methods).filter(method -> method.getName().startsWith("get") &&
                method.getParameterTypes().length == 0 && !void.class.equals(method.getReturnType()))
                .collect(Collectors.toList());
    }

    public static List<Method> getAllSetters(Object obj) {
        Method[] methods = obj.getClass().getMethods();
        return Arrays.stream(methods).filter(method -> method.getName().startsWith("set") &&
                method.getParameterTypes().length == 1).collect(Collectors.toList());
    }

}
