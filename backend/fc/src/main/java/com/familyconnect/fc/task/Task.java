package com.familyconnect.fc.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.familyconnect.fc.family.Family;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String taskName;
    private String taskDescription;
    private String taskCreatorUserName;
    private String taskAssigneeUserName;
    private String taskStartDate; // format: yyyy-MM-dd
    private String taskDueDate; // format: yyyy-MM-dd
    private int taskRewardPoints;

    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family;

    public Task(String taskName, String taskDescription, String taskCreatorUserName, String taskAssigneeUserName, String taskDueDate, int taskRewardPoints, Family family) {
        super();
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskCreatorUserName = taskCreatorUserName;
        this.taskAssigneeUserName = taskAssigneeUserName;
        this.taskDueDate = taskDueDate;
        this.taskRewardPoints = taskRewardPoints;
        this.family = family;
        this.taskStartDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now()); 
    }

    public Task(CreateTaskDTO createTaskDTO, Family family) {
        super();
        this.taskName = createTaskDTO.getTaskName();
        this.taskDescription = createTaskDTO.getTaskDescription();
        this.taskCreatorUserName = createTaskDTO.getTaskCreatorUserName();
        this.taskAssigneeUserName = createTaskDTO.getTaskAssigneeUserName();
        this.taskDueDate = createTaskDTO.getTaskDueDate();
        this.taskRewardPoints = createTaskDTO.getTaskRewardPoints();
        this.family = family;
        this.taskStartDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now());
    }

    public Task() {
        super();
    }

    // Getter ve Setter metotları

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskCreatorUserName() {
        return taskCreatorUserName;
    }

    public void setTaskCreatorUserName(String taskCreatorUserName) {
        this.taskCreatorUserName = taskCreatorUserName;
    }

    public String getTaskAssigneeUserName() {
        return taskAssigneeUserName;
    }



    public void setTaskAssigneeUserName(String taskAssigneeUserName) {
        this.taskAssigneeUserName = taskAssigneeUserName;
    }

    public String getTaskDueDate() {
        return taskDueDate;
    }

    public void setTaskDueDate(String taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    public int getTaskRewardPoints() {
        return taskRewardPoints;
    }

    public void setTaskRewardPoints(int taskRewardPoints) {
        this.taskRewardPoints = taskRewardPoints;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }


}
