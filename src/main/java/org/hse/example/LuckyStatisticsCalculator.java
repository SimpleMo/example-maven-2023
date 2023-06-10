package org.hse.example;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Вычисляет статистику по счастливым билетам
 */
public interface LuckyStatisticsCalculator {
    /**
     * Вычисляет статистику по счастливым билетам
     *
     * @return полученная статистика в виде {@link Map}
     */
    Map<Integer, Integer> getStatistic();
}

/**
 * Реализация {@link LuckyStatisticsCalculator}
 */
@Service
@NoArgsConstructor
class LuckyStatisticsCalculatorImpl implements LuckyStatisticsCalculator {
    @Autowired
    private Supplier<Stream<Ticket>> ticketsSupplier;

    @Getter(lazy = true, value = AccessLevel.PRIVATE)
    private final Stream<Ticket> tickets = prepare();

    private  Stream<Ticket> prepare() {
        return ticketsSupplier.get();
    }

    /**
     * Вычисляет статистику по счастливым билетам
     *
     * @return полученная статистика в виде {@link Map}
     */
    @Override
    public Map<Integer, Integer> getStatistic() {
        Map<Integer, Integer> result = new ConcurrentHashMap<>(new HashMap<>());
        getTickets()
                .map(Ticket::getNumber)
                .map(TicketUtils::getDigitsSum)
                .map(sum -> sum / 2)
                .forEach(num -> {
                    result.computeIfAbsent(num, key -> 0);
                    result.computeIfPresent(num, (key, val) -> ++val);
                });

        return result;
    }
}
