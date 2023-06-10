package org.hse.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Вычисляет количество счастливых билетов
 */
public class Main {

    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(Config.class);
        var counter = context.getBean("counterEight", Counter.class); // 4 816 030
//        var counter = context.getBean("multiplesOfThree", Counter.class); // 1 605 992
//        var counter = context.getBean("multiplesOfFive", Counter.class); // 949 340
//        var counter = context.getBean("multiplesOfSeven", Counter.class); // 688 002

        var counters = context.getBeansOfType(Counter.class).values();

        counters.forEach(c -> {
            var start = System.currentTimeMillis();
            var count = c.getCount();
            var end = System.currentTimeMillis();

            System.out.printf("Всего %d счастливых билетов.\nВремя работы метода %d мс.\n", count, end - start);

            start = System.currentTimeMillis();
            count = c.getCount();
            end = System.currentTimeMillis();

            System.out.printf("Всего %d счастливых билетов.\nВремя работы метода %d мс.\n", count, end - start);
        });

        var statisticsCalculator = context.getBean(LuckyStatisticsCalculator.class);

        var start = System.currentTimeMillis();
        var statistics = statisticsCalculator.getStatistic();
        var end = System.currentTimeMillis();

        System.out.printf("Счастливые билеты в разрезе суммы цифр\n%s\nВремя работы метода %d мс.\n", statistics, end - start);
    }
}