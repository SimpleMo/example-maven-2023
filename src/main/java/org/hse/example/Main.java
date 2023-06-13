package org.hse.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Вычисляет количество счастливых билетов
 */
@SpringBootApplication
@EnableJpaRepositories
@EntityScan("org.hse.example.entities")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}