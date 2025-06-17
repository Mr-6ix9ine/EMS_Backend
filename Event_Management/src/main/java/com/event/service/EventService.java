package com.event.service;

import com.event.entity.Event;
import com.event.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventService {

    public Event createEvent(Event event);
    public Optional<Event> getEventById(Long eventID);
    public List<Event> findByCategory(String category);
    public List<Event> findByLocation(String location);
    public List<Event> findByDate(LocalDate date);
    public List<Event> getAllEvents();
    public Optional<Event> updateEvent(Long eventID, Event eventDetails);
    public boolean deleteEvent(Long eventID);


}
