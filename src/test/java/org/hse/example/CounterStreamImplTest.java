package org.hse.example;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CounterStreamImplTest {

    @Mock
    private Ticket ticket;

    @Test
    public void testCount() {
        // given
        var counter = new CounterStreamImpl(6);
        var expected = (int) Math.pow(10, counter.getLength());
//        Ticket.TicketImpl ticket = Mockito.mock(Ticket.TicketImpl.class);
        Mockito.mock(Ticket.class);

        Mockito.when(this.ticket.isLucky()).thenReturn(true);
        counter.setToTicket(num -> ticket);
//        Ticket ticket100500 = Ticket.getInstance(counter.getLength(), 100500);
//        Ticket ticketThrow = Ticket.getInstance(counter.getLength(), 100500);
//
//        Mockito.when(ticket.getDigitsSum(Mockito.any(Ticket.class))).thenReturn(0);
//        Mockito.when(ticket.getDigitsSum(ticket100500)).thenReturn(6);
//        Mockito.when(ticket.getDigitsSum(Mockito.eq(ticket100500))).thenReturn(6);
//        Mockito.when(ticket.getDigitsSum(ticketThrow)).thenThrow(RuntimeException.class);
//
//        Mockito
//                .when(ticket.isLucky())
//                .thenAnswer(invocationOnMock -> {
////                    invocationOnMock.getArgument(0, Integer.class);
//                    return true;
//                });
//        Mockito.doNothing().when(ticket).print();
//        Mockito.doAnswer(invocationOnMock -> null).when(ticket).print();


        Mockito.spy(Ticket.class);
        Mockito.spy(new FakeTicket(1001, counter.getLength()));

        // when
        var actual = counter.count();

        // then
//        Mockito.verify(ticket, Mockito.times(0)).print();
        assertEquals(expected, actual);
    }

    @Value
    @RequiredArgsConstructor
    static class FakeTicket implements Ticket {
        int number;
        int length;

        @Override
        public boolean isLucky() {
            return true;
        }
    }

}