package org.example;

public interface Calculator{
    /**
     * Расчет факториала числа.
     * @param number
     */
    @Metric
    int calc (int number);

    int calcWithoutAnnotation(int number);
}
