package org.example;

import lombok.Getter;
import lombok.Setter;

public class CalculatorImpl implements Calculator {

    private int someVariable;
    public static final String FRIDAY = "FRIDAY";
    public static final String SATURDAY = "SATURDAY";
    public static final String SUNDAY = "SUNDAY";
    public static final String BAD_CONSTANT = "BAD";

    @Override
    public int calc (int number) {
//        Thread.sleep(1000);
        if (number <= 1) {
            return 1;
        }
        else {
            return number * calc(number - 1);
        }
    }

    @Override
    public int calcWithoutAnnotation(int number) {
//        Thread.sleep(1000);
        if (number <= 1) {
            return 1;
        }
        else {
            return number * calc(number - 1);
        }
    }

    public int getSomeVariable() {
        return someVariable;
    }

    public void setSomeVariable(int someVariable) {
        this.someVariable = someVariable;
    }
}
