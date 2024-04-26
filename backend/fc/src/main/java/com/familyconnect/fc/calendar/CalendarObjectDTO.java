package com.familyconnect.fc.calendar;
import java.time.OffsetDateTime;

import com.familyconnect.fc.utils.Enums.TaskStatus;


public class CalendarObjectDTO {
    private String name;
    private String description;
    private OffsetDateTime dueDate;
    private String type;
    private Integer id;
    private Integer priority;
    private OffsetDateTime startDate;
    private TaskStatus status;

    public CalendarObjectDTO(String name, String description, OffsetDateTime dueDate, String type, Integer id, Integer priority, OffsetDateTime startDate, TaskStatus status) {
        super();
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.type = type;
        this.id = id;
        this.priority = priority;
        this.startDate = startDate;
        this.status = status;

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

    public OffsetDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(OffsetDateTime eventDate) {
        this.dueDate = eventDate;
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }


}
