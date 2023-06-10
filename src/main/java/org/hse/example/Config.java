package org.hse.example;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Настройки приложения
 */
@Configuration("javaConfiguration")
@ComponentScan({"org.hse.example"})
public class Config {
    @Primary
    @Bean("counterSix")
    Counter getCounterSix() {
        return new CounterStreamImpl(6, Optional.empty());
    }
    @Bean("counterEight")
    Counter getCounterEight() {
        return new CounterStreamImpl(8, Optional.of(num -> new Ticket.TicketImpl(8, num)));
    }

    @Bean
    Supplier<Stream<Ticket>> getTicketStreamSupplier(@Qualifier("counterEight")
                                                     final Counter counter,
                                                     final Map<String, Counter> counters) {
        if (counter instanceof CounterStreamImpl counterStreamImpl) {
            return counterStreamImpl;
        }
        return Stream::empty;
    }
}
