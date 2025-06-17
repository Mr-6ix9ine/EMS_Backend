package com.event.service;

import com.event.entity.Event;
import com.event.repo.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service // Marks this class as a Spring Service component
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;
    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Optional<Event> getEventById(Long eventID) {
        return eventRepository.findById(eventID);
    }

    public List<Event> findByCategory(String category)
    {
        return  eventRepository.findByCategory(category);

    }
    public List<Event> findByLocation(String location)
    {
        return  eventRepository.findByLocation(location);

    }
    public List<Event> findByDate(LocalDate date)
    {
        return  eventRepository.findByDate(date);

    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> updateEvent(Long eventID, Event eventDetails) {
        Optional<Event> optionalEvent = eventRepository.findById(eventID);
        if (optionalEvent.isPresent()) {
            Event existingEvent = optionalEvent.get();
            existingEvent.setName(eventDetails.getName());
            existingEvent.setCategory(eventDetails.getCategory());
            existingEvent.setLocation(eventDetails.getLocation());
            existingEvent.setDate(eventDetails.getDate());
            existingEvent.setOrganizerID(eventDetails.getOrganizerID());
            return Optional.of(eventRepository.save(existingEvent));
        }
        return Optional.empty(); // Event not found
    }
    public boolean deleteEvent(Long eventID) {
        if (eventRepository.existsById(eventID)) {
            eventRepository.deleteById(eventID);
            return true;
        }
        return false;
    }


}

