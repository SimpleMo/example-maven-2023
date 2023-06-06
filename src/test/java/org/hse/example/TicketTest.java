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

    /**
     * Проверяет работу метода {@link Ticket#isLucky()} в случае, когда билет не является счастливым
     */
    @Test
    public void testIsNotLucky() {
        // given
        val number = 100500;
        val length = 6;
        val ticket = Ticket.getInstance(length, number);

        // when
        var isLucky = ticket.isLucky();

        // then
        assertFalse("Билет не является счастливым", isLucky);
    }

    /**
     * Проверяет работу метода {@link Ticket#isLucky()} в случае, когда билет является счастливым
     */
    @Test
    public void testIsLucky() {
        // given
        val number = 111111;
        val length = 6;
        val ticket = Ticket.getInstance(length, number);

        // when
        var isLucky = ticket.isLucky();

        // then
        assertTrue("Билет является счастливым", isLucky);
    }

    /**
     * Проверяет, что метод {@link Ticket#isLucky()} корректно работает для "коротких" номеров билетов
     */
    @Test
    public void testIsLuckyNumberLengthLessThanTicketLength() {
        // given
        val number = 1001;
        val length = 6;
        val ticket = Ticket.getInstance(length, number);

        // when
        var isLucky = ticket.isLucky();

        // then
        assertTrue("Билет является счастливым", isLucky);
    }

    /**
     * Проверяет, что билет с нечётной длиной не может быть счастливым
     */
    @Test
    public void testIsLuckyOddLength() {
        // given
        val number = 123321;
        val length = 7;
        val ticket = Ticket.getInstance(length, number);

        // when
        var isLucky = ticket.isLucky();

        // then
        assertFalse("Билет не является счастливым", isLucky);
    }
}