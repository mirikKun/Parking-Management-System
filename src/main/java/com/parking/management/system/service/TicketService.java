package com.parking.management.system.service;

import com.parking.management.system.dao.TicketDao;
import com.parking.management.system.domain.Ticket;
import com.parking.management.system.domain.Ticket;
import com.parking.management.system.exception.EntityNotFoundException;
import com.parking.management.system.exception.PaymentIdNotUniqueException;
import com.parking.management.system.exception.UsernameNotUniqueException;

import java.util.List;
import java.util.Optional;

public class TicketService {
    private final TicketDao ticketDao;

    public TicketService(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    public Optional<Ticket> getById(int id) {
        return ticketDao.getById(id);
    }

    public List<Ticket> getAll() {
        return ticketDao.getAll();
    }

    public void save(Ticket ticket) {
        verifyTicketUnique(ticket);
        ticketDao.save(ticket);
    }

    public void update(Ticket ticket) {
        verifyTicketUnique(ticket);
        verifyTicketPresent(ticket.getId());
        ticketDao.update(ticket);
    }

    public void delete(int id) {
        ticketDao.delete(id);
    }

    private void verifyTicketPresent(int id) {
        ticketDao.getById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Ticket with id %d is not present", id)));
    }
    private void verifyTicketUnique(Ticket ticket) {
        ticketDao.getByPaymentId(ticket.getPaymentId()).ifPresent(ticketWithSameUsername -> {
            if (ticket.getId() != ticketWithSameUsername.getId()) {
                throw new PaymentIdNotUniqueException(String.format("Ticket with payment Id %s already exist", ticket.getPaymentId()));
            }
        });
    }
}
