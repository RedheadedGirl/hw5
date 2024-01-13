package org.example;

public class CalculatorImpl implements Calculator {

    private int someVariable;
    private boolean someBool;
    public static final String FRIDAY = "FRIDAY";
    public static final String SATURDAY = "SATURDAY";
    public static final String SUNDAY = "SUNDAY";
    public static final String BAD_CONSTANT = "BAD";

    @Override
    public int calc (int number) {
        if (number <= 1) {
            return 1;
        }
        else {
            return number * calc(number - 1);
        }
    }

    @Override
    public int calcWithoutAnnotation(int number) {
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

    public boolean isSomeBool() {
        return someBool;
    }

    public void setSomeBool(boolean someBool) {
        this.someBool = someBool;
    }
}
