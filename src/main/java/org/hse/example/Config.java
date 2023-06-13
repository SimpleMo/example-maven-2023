package org.hse.example;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
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
    Supplier<Stream<Ticket>> getTicketStreamSupplier(@Qualifier("counterEight") final Counter counter) {
        if (counter instanceof CounterStreamImpl counterStreamImpl) {
            return counterStreamImpl;
        }
        return Stream::empty;
    }

    @Bean("multiplesOfThree")
    Counter getMultiplesOfThreeCounter(final Supplier<Stream<Ticket>> ticketStreamSupplier,
                                       @Qualifier("multiplesOfThreeChecker")
                                       final Predicate<Ticket> multiplicityChecker) {
        return new CounterMultiplicityImpl(ticketStreamSupplier.get(), multiplicityChecker);
    }

    @Bean("multiplesOfThreeChecker")
    Predicate<Ticket> getMultiplesOfThreeChecker() {
        return ((Function<Ticket, Integer>) Ticket::getNumber).andThen(num -> num % 3 == 0)::apply;
    }

    @Bean("multiplesOfFive")
    Counter getMultiplesOfFiveCounter(final Supplier<Stream<Ticket>> ticketStreamSupplier,
                                      @Qualifier("multiplesOfFiveChecker")
                                      final Predicate<Ticket> multiplicityChecker) {
        return new CounterMultiplicityImpl(ticketStreamSupplier.get(), multiplicityChecker);
    }

    @Bean("multiplesOfFiveChecker")
    Predicate<Ticket> getMultiplesOfFiveChecker() {
        return ((Function<Ticket, Integer>) Ticket::getNumber).andThen(num -> num % 5 == 0)::apply;
    }

    @Bean("multiplesOfSeven")
    Counter getMultiplesOfSevenCounter(final Supplier<Stream<Ticket>> ticketStreamSupplier,
                                       @Qualifier("multiplesOfSevenChecker")
                                       final Predicate<Ticket> multiplicityChecker) {
        return new CounterMultiplicityImpl(ticketStreamSupplier.get(), multiplicityChecker);
    }

    @Bean("multiplesOfSevenChecker")
    Predicate<Ticket> getMultiplesOfSevenChecker() {
        return ((Function<Ticket, Integer>) Ticket::getNumber).andThen(num -> num % 7 == 0)::apply;
    }
}
