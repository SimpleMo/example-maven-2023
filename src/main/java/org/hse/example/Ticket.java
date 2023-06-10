package org.hse.example;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


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
    @Component
    @Accessors(chain = true)
    @AllArgsConstructor
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class TicketImpl implements Ticket {
        TicketImpl() {
            System.out.println("This is ticket");
        }

        /**
         * Количество цифр в номере билета
         */
        @Setter(AccessLevel.PACKAGE)
        int length;

        /**
         * Номер билета
         */
        @Setter(AccessLevel.PACKAGE)
        int number;

        int getDigitsSum(final Ticket ticket) {
            return TicketUtils.getDigitsSum(ticket).orElse(0);
        }

        void print() {
            System.out.printf("Билет №%d\n", 100500);
        }

        /**
         * Вычисляет, является ли билет счастливым
         *
         * @return true, если является
         */
        @Override
        public boolean isLucky() {
            // todo покрыть тестами
            if (getLength() % 2 != 0) {
                return false;
            }

            int middle = getLength() / 2;
            int half = Double.valueOf(Math.pow(10, middle)).intValue();

            int first = TicketUtils.getDigitsSum(getNumber() / half);
            int last = TicketUtils.getDigitsSum(getNumber() % half);

            return first == last;
        }
    }

}
