package org.hse.example;

import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Тесты для {@link Ticket}
 */
public class TicketTest {

    /**
     * Проверяет, что при корректных входных данных корректно создаются билеты
     */
    @Test
    public void testGetInstanceTicketCreated() {
        // given
        val number = 100500;
        val length = 6;

        // when
        val ticket = Ticket.getInstance(length, number);

        // then
        assertNotNull("Должен вернуться объект", ticket);
        assertEquals("Не тот номер", number, ticket.getNumber());
        assertEquals("Не та длина", length, ticket.getLength());
    }

    /**
     * Проверяет, что отрицательные номера обрабатываются по модулю
     */
    @Test
    public void testGetInstanceNegativeNumber() {
        // given
        val number = -100500;
        val length = 6;
        val modulus = Math.abs(number);

        // when
        val ticket = Ticket.getInstance(length, number);

        // then
        assertNotNull("Должен вернуться объект", ticket);
        assertEquals("Не тот номер", modulus, ticket.getNumber());
        assertEquals("Не та длина", length, ticket.getLength());
    }

    /**
     * Проверяет, что отрицательная длина обрабатывается по модулю
     */
    @Test
    public void testGetInstanceNegativeLength() {
        // given
        val number = 100500;
        val length = -6;
        val modulus = Math.abs(length);

        // when
        val ticket = Ticket.getInstance(length, number);

        // then
        assertNotNull("Должен вернуться объект", ticket);
        assertEquals("Не тот номер", number, ticket.getNumber());
        assertEquals("Не та длина", modulus, ticket.getLength());
    }

    /**
     * Проверяет, что не создаются билеты, номера которых не соответствуют длине
     */
    @Test
    public void testGetInstanceWrongLength() {
        // given
        val number = 100500;
        val length = 3;

        // when
        try {
            val ticket = Ticket.getInstance(length, number);
        } catch (Exception e) {
            // then
            assertEquals("Нужно IllegalArgumentException", IllegalArgumentException.class, e.getClass());
            assertEquals("Должен быть нужный текст", "Количество цифр в номере 100500 больше 3", e.getMessage());
        }
    }
}