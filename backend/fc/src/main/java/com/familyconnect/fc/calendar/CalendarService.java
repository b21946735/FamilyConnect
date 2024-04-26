package com.familyconnect.fc.calendar;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.familyconnect.fc.event.Event;
import com.familyconnect.fc.event.EventRepository;
import com.familyconnect.fc.family.Family;
import com.familyconnect.fc.family.FamilyRepository;
import com.familyconnect.fc.progress.Progress;
import com.familyconnect.fc.progress.ProgressService;
import com.familyconnect.fc.task.TaskRepository;
import com.familyconnect.fc.user.ApplicationUser;
import com.familyconnect.fc.user.UserRepository;
import com.familyconnect.fc.utils.Enums.TaskStatus;
import java.util.ArrayList;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
@Transactional
public class CalendarService {
    
        @Autowired
        private FamilyRepository familyRepository;
    
        @Autowired
        private UserRepository userRepository;

        @Autowired
        private EventRepository eventRepository;


        public List<CalendarObjectDTO> getCalendar(String username){
            if(!userRepository.findByUsername(username).isPresent()){
                return null;
            }
            ApplicationUser user = userRepository.findByUsername(username).get();
            Optional<Family> familyOptional = familyRepository.findById(user.getFamilyId());
            if(!familyOptional.isPresent()){
                return null;
            }
            Family family = familyOptional.get();
            

            List<CalendarObjectDTO> calendar = new ArrayList<>();

            // get all tasks then add to calendar
            family.getTasks().forEach(task -> {
                CalendarObjectDTO calendarObject = new CalendarObjectDTO(task.getTaskName(), task.getTaskDescription(),  task.getTaskDueDate(),"Task",task.getId());
                calendar.add(calendarObject);
            });

            // get all events then add to calendar
            List<Event> events = eventRepository.findByFamilyId(family.getId());

            events.forEach(event -> {
                try {
                    CalendarObjectDTO calendarObject = new CalendarObjectDTO(event.getName(), event.getDescription(),
                        OffsetDateTime.ofInstant(event.getEventDate().toInstant(), ZoneOffset.UTC) ,"Event",event.getId());
                    calendar.add(calendarObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            });

            // sort calendar by date
            calendar.sort((CalendarObjectDTO c1, CalendarObjectDTO c2) -> c1.getEventDate().compareTo(c2.getEventDate()));

        
            
            return calendar;

        }

}
