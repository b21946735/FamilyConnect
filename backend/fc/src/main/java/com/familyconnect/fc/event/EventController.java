package com.familyconnect.fc.event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/create")
    public ResponseEntity<Event> createEvent(@RequestBody EventCreateDTO eventDTO) {
        Event createdEvent = eventService.createEvent(eventDTO);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @GetMapping("/family/{familyId}")
    public ResponseEntity<List<Event>> getEventsByFamilyId(@PathVariable(value = "familyId") Integer familyId) {
        List<Event> events = eventService.getEventsByFamilyId(familyId);
        return ResponseEntity.ok().body(events);
    }

    @DeleteMapping("/delete/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable(value = "eventId") Integer eventId) {
        eventService.deleteEventById(eventId);
        return ResponseEntity.ok("Event with ID " + eventId + " deleted successfully.");
    }

    @PutMapping("/updateEvent/{eventId}")
    public ResponseEntity<Event> updateEvent(@PathVariable(value = "eventId") Integer eventId, @RequestBody Event eventDetails) {
        Event updatedEvent = eventService.updateEvent(eventId, eventDetails);
        return ResponseEntity.ok(updatedEvent);
    }

}

