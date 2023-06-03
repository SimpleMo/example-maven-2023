package org.hse.example;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;


/**
 * Сущность Билет
 */
public interface Ticket extends Lucky {

    static Ticket getInstance(final int length, final int number) {
        var lengthModulus = Math.abs(length);
        var maxNumber = Math.pow(10, lengthModulus) - 1;
        if (number > maxNumber) {
            throw new IllegalArgumentException(String.format("Количество цифр в номере %d больше %d", number, length));
        }

        return new TicketImpl(lengthModulus, Math.abs(number));
    }

    int getLength();

    int getNumber();

    /**
     * Реализация Сущности Билет
     */
    @Data
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    class TicketImpl implements Ticket {

        /**
         * Количество цифр в номере билета
         */
        @Setter(AccessLevel.NONE)
        int length;

        /**
         * Номер билета
         */
        @Setter(AccessLevel.NONE)
        int number;

        /**
         * Вычисляет, является ли билет счастливым
         *
         * @return true, если является
         */
        @Override
        public boolean isLucky() {
            // todo покрыть тестами
            int middle = getLength() / 2;
            int half = Double.valueOf(Math.pow(10, middle)).intValue();

            int first = TicketUtils.getDigitsSum(getNumber() / half);
            int last = TicketUtils.getDigitsSum(getNumber() % half);

            return first == last;
        }
    }

}
