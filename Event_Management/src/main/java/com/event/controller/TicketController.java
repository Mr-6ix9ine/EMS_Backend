package com.event.controller;

import com.event.entity.Ticket;
import com.event.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tickets") // Base URL for ticket-related endpoints
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }


    @PostMapping("/book")
    public ResponseEntity<?> bookTicket(@RequestBody Map<String, Long> bookingRequest) {
        Long eventId = bookingRequest.get("eventId");
        Long userId = bookingRequest.get("userId");

        if (eventId == null || userId == null) {
            return new ResponseEntity<>("Event ID and User ID are required for booking.", HttpStatus.BAD_REQUEST);
        }

        try {
            Ticket bookedTicket = ticketService.bookTicket(eventId, userId);
            return new ResponseEntity<>(bookedTicket, HttpStatus.CREATED); // 201 Created
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); // 404 Not Found
        } catch (Exception e) {
            return new ResponseEntity<>("Error booking ticket: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }


    @GetMapping("/{ticketId}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long ticketId) {
        return ticketService.getTicketById(ticketId)
                .map(ticket -> new ResponseEntity<>(ticket, HttpStatus.OK)) // 200 OK
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)); // 404 Not Found
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Ticket>> getTicketsByUserId(@PathVariable Long userId) {
        List<Ticket> tickets = ticketService.getTicketsByUserId(userId);
        if (tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content, or 404 Not Found if you prefer
        }
        return new ResponseEntity<>(tickets, HttpStatus.OK); // 200 OK
    }


    @PatchMapping("/{ticketId}/cancel") // PATCH is suitable for partial updates (changing status)
    public ResponseEntity<?> cancelTicket(@PathVariable Long ticketId) {
        try {
            Optional<Ticket> canceledTicket = ticketService.cancelTicket(ticketId);
            if (canceledTicket.isPresent()) {
                return new ResponseEntity<>(canceledTicket.get(), HttpStatus.OK); // 200 OK
            } else {
                return new ResponseEntity<>("Ticket not found or already canceled/invalid status for cancellation.", HttpStatus.BAD_REQUEST); // 400 Bad Request
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); // 404 Not Found
        } catch (Exception e) {
            return new ResponseEntity<>("Error canceling ticket: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }


    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        if (tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }
}
