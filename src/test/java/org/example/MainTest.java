package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    @Test
    void shouldGetAllMethods() {
        CalculatorImpl calculator = new CalculatorImpl();
        List<String> allMethods = Main.getAllMethods(calculator);
        assertThat(allMethods).containsAll(List.of("getSomeVariable", "calc", "wait", "wait", "wait", "equals",
                "toString", "hashCode", "getClass", "notify", "notifyAll"));
    }

    @Test
    void shouldGetAllGetters() {
        CalculatorImpl calculator = new CalculatorImpl();
        List<String> allMethods = Main.getAllGetters(calculator);
        assertThat(allMethods).containsAll(List.of("getSomeVariable", "getClass"));
    }

    @Test
    void shouldCheckAllConstantsNames() {
        CalculatorImpl calculator = new CalculatorImpl();
        List<String> allConstants = Main.checkAllConstantsNames(calculator);
        assertThat(allConstants).containsAll(List.of("FRIDAY", "SATURDAY", "SUNDAY"));
    }

    @Test
    void shouldSetGetter() {
        Cat catFrom = new Cat();
        catFrom.setSound("meaaaw");
        Cat catTo = new Cat();
        BeanUtils.assign(catTo, catFrom);
        assertEquals(catTo.getSound(), catFrom.getSound());
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
