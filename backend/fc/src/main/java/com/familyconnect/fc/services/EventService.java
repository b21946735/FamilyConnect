package com.familyconnect.fc.services;

import com.familyconnect.fc.models.Event;
import com.familyconnect.fc.models.EventCreateDTO;
import com.familyconnect.fc.models.Family;
import com.familyconnect.fc.repository.EventRepository;
import com.familyconnect.fc.repository.FamilyRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private FamilyRepository familyRepository;
    
    public Event createEvent(EventCreateDTO eventDTO) {
        Integer familyId = eventDTO.getFamilyId();
        familyRepository.findById(familyId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found for this id :: " + familyId));

        Event event = new Event(
                eventDTO.getName(),
                eventDTO.getDescription(),
                eventDTO.getEventDate(),
                eventDTO.getFamilyId());
        return eventRepository.save(event);
    }

    public List<Event> getEventsByFamilyId(Integer familyId) {
        familyRepository.findById(familyId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found for this id :: " + familyId));

        return eventRepository.findByFamilyId(familyId);
    }

    public void deleteEventById(Integer eventId) {
        eventRepository.findById(eventId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found for this id :: " + eventId));

        eventRepository.deleteById(eventId);
    }

    public Event updateEvent(Integer eventId, Event eventDetails) {
        Event event = eventRepository.findById(eventId)
                .map(existingEvent -> {
                    existingEvent.setName(eventDetails.getName());
                    existingEvent.setDescription(eventDetails.getDescription());
                    existingEvent.setEventDate(eventDetails.getEventDate());
                    return eventRepository.save(existingEvent);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found for this id :: " + eventId));
        
        return event;   
    }
}

