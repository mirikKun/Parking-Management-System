package com.parking.management.system.service;

import com.parking.management.system.dao.TicketDao;
import com.parking.management.system.domain.Ticket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketDao ticketDao;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void givenIdOfTheFirstTicket_whenGetById_thenReturnedTicketWithGivenId() {
        Optional<Ticket> expectedTicket = Optional.of(new Ticket(1, LocalDate.parse("2019-07-13"), 1));
        when(ticketDao.getById(1)).thenReturn(expectedTicket);

        Optional<Ticket> actualTicket = ticketService.getById(1);

        verify(ticketDao, times(1)).getById(1);
        assertEquals(expectedTicket, actualTicket);
    }

    @Test
    void getAll() {
        List<Ticket> expectedTickets = singletonList(new Ticket(1, LocalDate.parse("2019-07-13"), 1));
        when(ticketDao.getAll()).thenReturn(expectedTickets);

        List<Ticket> actualTickets = ticketService.getAll();

        verify(ticketDao, times(1)).getAll();
        assertEquals(expectedTickets, actualTickets);
    }

    @Test
    void save() {
        Ticket ticket = new Ticket(1, LocalDate.parse("2019-07-13"), 1);
        when(ticketDao.getByPaymentId(1)).thenReturn(Optional.empty());

        ticketService.save(ticket);

        verify(ticketDao, times(1)).save(ticket);
    }

    @Test
    void update() {
        Ticket ticket = new Ticket(1, LocalDate.parse("2019-07-13"), 1);
        when(ticketDao.getById(1)).thenReturn(Optional.of(ticket));
        when(ticketDao.getByPaymentId(1)).thenReturn(Optional.empty());

        ticketService.update(ticket);

        verify(ticketDao, times(1)).update(ticket);
    }
}