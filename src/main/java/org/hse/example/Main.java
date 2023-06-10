package org.hse.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Вычисляет количество счастливых билетов
 */
public class Main {

    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(Config.class);
        var counter = context.getBean("counterEight", Counter.class);

        var start = System.currentTimeMillis();
        var count = counter.getCount();
        var end = System.currentTimeMillis();

        System.out.printf("Всего %d счастливых билетов.\nВремя работы метода %d мс.\n", count, end - start);

        start = System.currentTimeMillis();
        count = counter.getCount();
        end = System.currentTimeMillis();

        System.out.printf("Всего %d счастливых билетов.\nВремя работы метода %d мс.\n", count, end - start);

        var statisticsCalculator = context.getBean(LuckyStatisticsCalculator.class);

        start = System.currentTimeMillis();
        var statistics = statisticsCalculator.getStatistic();
        end = System.currentTimeMillis();

        System.out.printf("Счастливые билеты в разрезе суммы цифр\n%s\nВремя работы метода %d мс.\n", statistics, end - start);
    }
}