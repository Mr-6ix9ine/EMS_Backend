package com.event.service;

import com.event.entity.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketService {

    public Ticket bookTicket(Long eventId, Long userId);
    public Optional<Ticket> getTicketById(Long ticketId);
    public List<Ticket> getTicketsByUserId(Long userId);
    public Optional<Ticket> cancelTicket(Long ticketId);
    public List<Ticket> getAllTickets();
}
