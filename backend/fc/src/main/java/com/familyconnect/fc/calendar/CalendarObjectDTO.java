package com.familyconnect.fc.calendar;
import java.time.OffsetDateTime;


public class CalendarObjectDTO {
    private String name;
    private String description;
    private OffsetDateTime eventDate;

    private String type;

    private Integer id;

    public CalendarObjectDTO(String name, String description, OffsetDateTime eventDate, String type, Integer id) {
        super();
        this.name = name;
        this.description = description;
        this.eventDate = eventDate;
        this.type = type;
        this.id = id;
    }

    public CalendarObjectDTO() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OffsetDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(OffsetDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
