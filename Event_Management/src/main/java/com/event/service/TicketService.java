package com.event.service;
import com.event.entity.Event;
import com.event.entity.Ticket;
import com.event.entity.User;
import com.event.repo.EventRepository;
import com.event.repo.TicketRepository;
import com.event.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository; // To fetch Event details
    private final UserRepository userRepository;   // To fetch User details

    @Autowired
    public TicketService(TicketRepository ticketRepository,
                         EventRepository eventRepository,
                         UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public Ticket bookTicket(Long eventId, Long userId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with ID: " + eventId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        Ticket newTicket = new Ticket();
        newTicket.setEvent(event);
        newTicket.setUser(user);
        newTicket.setBookingDate(LocalDateTime.now());
        newTicket.setStatus(Ticket.TicketStatus.CONFIRMED);
        return ticketRepository.save(newTicket);
    }


    public Optional<Ticket> getTicketById(Long ticketId) {
        return ticketRepository.findById(ticketId);
    }


    public List<Ticket> getTicketsByUserId(Long userId) {
        return ticketRepository.findByUser_Id(userId);
    }


    @Transactional
    public Optional<Ticket> cancelTicket(Long ticketId) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            if (ticket.getStatus() == Ticket.TicketStatus.CONFIRMED) {
                ticket.setStatus(Ticket.TicketStatus.CANCELED);

                return Optional.of(ticketRepository.save(ticket));
            } else {

                return Optional.empty();
            }
        }
        return Optional.empty(); // Ticket not found
    }


    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}
