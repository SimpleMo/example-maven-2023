package org.hse.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Настройки приложения
 */
@Configuration
public class Config {
    @Bean("counterSix")
    Counter getCounterSix() {
        return new CounterStreamImpl(6);
    }
    @Bean("counterEight")
    Counter getCounterEight() {
        return new CounterStreamImpl(8);
    }
}
