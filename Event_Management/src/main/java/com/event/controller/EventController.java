package com.event.controller;


import com.event.entity.Event;
import com.event.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {

    private final EventService eventService;

    // Constructor injection for EventService
    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event createdEvent = eventService.createEvent(event);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable("id") Long id) {
        Optional<Event> event = eventService.getEventById(id);
        return event.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable("id") Long id, @RequestBody Event eventDetails) {
        Optional<Event> updatedEvent = eventService.updateEvent(id, eventDetails);
        return updatedEvent.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("id") Long id) {
        boolean deleted = eventService.deleteEvent(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/category")
    public ResponseEntity<List<Event>> getByCategory(@RequestParam String category) {
        List<Event> events = eventService.findByCategory(category);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
    @GetMapping("/location")
    public ResponseEntity<List<Event>> getByLocation(@RequestParam String location) {
        List<Event> events = eventService.findByLocation(location);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
    @GetMapping("/date")
    public ResponseEntity<List<Event>> getByLocation(@RequestParam LocalDate date) {
        List<Event> events = eventService.findByDate(date);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }


//    @GetMapping("/search")
//    public ResponseEntity<List<Event>> searchEvents(
//            @RequestParam(required = false) String category,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
//            @RequestParam(required = false) String location) {
//        List<Event> events = eventService.searchAndFilterEvents(category, date, location);
//        return new ResponseEntity<>(events, HttpStatus.OK);
//    }
}

