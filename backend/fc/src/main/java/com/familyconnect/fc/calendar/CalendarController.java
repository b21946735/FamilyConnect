package com.familyconnect.fc.calendar;

import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/calendar")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;


    @GetMapping("/getCalendar/{username}")
    public ResponseEntity getCalendar(@PathVariable String username){
        List<CalendarObjectDTO> calendar = calendarService.getCalendar(username);
        if(calendar == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No calendar record found");
        }
        return ResponseEntity.ok().body(calendar);
    }
}
