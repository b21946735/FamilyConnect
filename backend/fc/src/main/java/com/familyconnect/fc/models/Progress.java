package com.familyconnect.fc.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;

@Entity
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String progressName;
    private int quota;
    private int currentStatus; // Anlık durum
    private Date dueDate;
    private String createdBy;
    private String assignedTo;

    public Progress() {
        super();
    }

    public Progress(String progressName, int quota, int currentStatus, Date dueDate, String createdBy, String assignedTo) {
        this.progressName = progressName;
        this.quota = quota;
        this.currentStatus = currentStatus;
        this.dueDate = dueDate;
        this.createdBy = createdBy;
        this.assignedTo = assignedTo;
    }

    // Getter ve Setter metotları

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProgressName() {
        return progressName;
    }

    public void setProgressName(String progressName) {
        this.progressName = progressName;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public int getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(int currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
}