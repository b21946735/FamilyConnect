package com.familyconnect.fc.event;

import java.util.Date;

public class EventCreateDTO {

    private String name;
    private String description;
    private Date eventDate;
    private Integer familyId;

    public EventCreateDTO() {
        super();
    }

    public EventCreateDTO(String name, String description, Date eventDate, Integer familyId) {
        this.name = name;
        this.description = description;
        this.eventDate = eventDate;
        this.familyId = familyId;
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
    
    public Date getEventDate() {
        return eventDate;
    }
    
    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }
    
    public Integer getFamilyId() {
        return familyId;
    }
    
    public void setFamilyId(Integer familyId) {
        this.familyId = familyId;
    }
    
}
