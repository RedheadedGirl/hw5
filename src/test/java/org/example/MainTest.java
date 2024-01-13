package org.example;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    @Test
    void shouldGetAllMethods() {
        CalculatorImpl calculator = new CalculatorImpl();
        List<String> allMethods = Main.getAllMethods(calculator);
        assertThat(allMethods).containsAll(List.of("getSomeVariable", "isSomeBool", "setSomeVariable",
                "setSomeBool", "calcWithoutAnnotation", "calc", "wait", "wait", "wait", "equals", "toString",
                "hashCode", "getClass", "notify", "notifyAll"));
        assertEquals(15, allMethods.size());
    }

    @Test
    void shouldGetAllGetters() {
        CalculatorImpl calculator = new CalculatorImpl();
        List<String> allMethods = Main.getAllGetters(calculator);
        assertThat(allMethods).containsAll(List.of("getSomeVariable", "isSomeBool", "getClass"));
    }

    @Test
    void shouldCheckAllConstantsNames() {
        CalculatorImpl calculator = new CalculatorImpl();
        List<String> allConstants = Main.checkAllConstantsNames(calculator);
        assertThat(allConstants).containsAll(List.of("FRIDAY", "SATURDAY", "SUNDAY"));
    }

    @Test
    void shouldProxyCallsCache() {
        ClassLoader cl = CalculatorImpl.class.getClassLoader();
        Calculator proxy = (Calculator) Proxy.newProxyInstance(cl,
                new Class[] {Calculator.class}, new CachedCalculator(new CalculatorImpl()));
        System.out.println(proxy.calc(4));
        System.out.println(proxy.calc(3));
        System.out.println(proxy.calc(4));
    }

    @Test
    void shouldProxyCallsPerformance() {
        ClassLoader cl = CalculatorImpl.class.getClassLoader();
        Calculator performanceProxy = (Calculator) Proxy.newProxyInstance(cl,
                new Class[] {Calculator.class}, new PerformanceProxy(new CalculatorImpl()));
        System.out.println("with @Metric:");
        System.out.println(performanceProxy.calc(3));
        System.out.println("without annotation:");
        System.out.println(performanceProxy.calcWithoutAnnotation(3));
    }

    @Test
    void shouldSetGetter() {
        Cat catFrom = new Cat();
        catFrom.setSound("meaaaw");
        Cat catTo = new Cat();
        BeanUtils.assign(catTo, catFrom);
        assertEquals(catFrom.getSound(), catTo.getSound());
    }

    private class Cat {

        private String sound;

        public String getSound() {
            return sound;
        }

        public void setSound(String sound) {
            this.sound = sound;
        }
    }
}
