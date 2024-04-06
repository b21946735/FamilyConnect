package com.familyconnect.fc.progress;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class ProgressCreateDTO {
    
    private String progressName;
    private int quota;
    private int currentStatus;
    private Date dueDate;
    private String createdBy;
    private String assignedTo;

    // Constructor, getters ve setters

    public ProgressCreateDTO() {
        super();
    }

    public ProgressCreateDTO(
        String progressName,
         int quota,
         int currentStatus, 
         Date dueDate, 
         String createdBy,
         String assignedTo
         ) {
        this.progressName = progressName;
        this.quota = quota;
        this.currentStatus = currentStatus;
        this.dueDate = dueDate;
        this.createdBy = createdBy;
        this.assignedTo = assignedTo;
    }

    // Getters
    public String getProgressName() {
        return progressName;
    }

    public int getQuota() {
        return quota;
    }

    public int getCurrentStatus() {
        return currentStatus;
    }

    public OffsetDateTime getDueDate() {
        // convert Date to OffsetDateTime
        return OffsetDateTime.ofInstant(dueDate.toInstant(), ZoneOffset.UTC);
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    // Setters
    public void setProgressName(String progressName) {
        this.progressName = progressName;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public void setCurrentStatus(int currentStatus) {
        this.currentStatus = currentStatus;
    }
    
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
}

