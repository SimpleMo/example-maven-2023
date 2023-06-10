package org.hse.example;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
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
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class Config {
    ApplicationContext context;

    @Primary
    @Bean("counterSix")
    Counter getCounterSix() {
        return new CounterStreamImpl(6, Optional.empty());
    }
    @Bean("counterEight")
    Counter getCounterEight() {
        IntFunction<Ticket> toTicket = num -> {
            var ticket = context.getBean(Ticket.TicketImpl.class);
            return ticket.setLength(8).setNumber(num);
        };
        return new CounterStreamImpl(8, Optional.of(toTicket));
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
