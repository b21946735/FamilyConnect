package com.familyconnect.fc.progress;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.OffsetDateTime;
import java.util.List;

import com.familyconnect.fc.utils.Enums;

import java.util.ArrayList;




@Entity
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String progressName;
    private int quota;
    private int currentStatus; // Anlık durum
    private OffsetDateTime dueDate;
    private String createdBy;
    private String assignedTo;
    private List<String> rewards = new ArrayList<>();
    private Enums.ProgressStatus progressStatus;


    public Progress() {
        super();
    }

    public Progress(String progressName, int quota, int currentStatus, OffsetDateTime dueDate, String createdBy, String assignedTo, List<String> rewards) {
        this.progressName = progressName;
        this.quota = quota;
        this.currentStatus = currentStatus;
        this.dueDate = dueDate;
        this.createdBy = createdBy;
        this.assignedTo = assignedTo;
        this.rewards = rewards;
        this.progressStatus = Enums.ProgressStatus.IN_PROGRESS;
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

    public OffsetDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(OffsetDateTime dueDate) {
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

    public List<String> getRewards() {
        return rewards;
    }

    public void setRewards(List<String> rewards) {
        this.rewards = rewards;
    }

    public Enums.ProgressStatus getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(Enums.ProgressStatus progressStatus) {
        this.progressStatus = progressStatus;
    }
}