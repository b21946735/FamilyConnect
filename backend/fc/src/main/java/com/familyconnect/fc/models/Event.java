package com.familyconnect.fc.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private Date eventDate;
    private Integer familyId; 


    public Event() {
        super();
    }

    public Event(String name, String description, Date eventDate, Integer familyId) {
        this.name = name;
        this.description = description;
        this.eventDate = eventDate;
        this.familyId = familyId;
    }

    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
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

