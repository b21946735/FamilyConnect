package com.familyconnect.fc.task;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class CreateTaskDTO {
    private String taskName;
    private String taskDescription;
    private String taskCreatorUserName;
    
    private String taskAssigneeUserName;
    private Date taskDueDate; // format: yyyy-MM-dd

    private int taskRewardPoints;
    private int taskId;

    public CreateTaskDTO(String taskName, String taskDescription, String taskCreatorUserName, String taskAssigneeUserName, Date taskDueDate, int taskRewardPoints, int taskId) {
        super();
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskCreatorUserName = taskCreatorUserName;
        this.taskAssigneeUserName = taskAssigneeUserName;
        this.taskDueDate = taskDueDate;
        this.taskRewardPoints = taskRewardPoints;
        this.taskId = taskId;
    }

    public CreateTaskDTO(Task task) {
        super();
        this.taskName = task.getTaskName();
        this.taskDescription = task.getTaskDescription();
        this.taskCreatorUserName = task.getTaskCreatorUserName();
        this.taskAssigneeUserName = task.getTaskAssigneeUserName();
        this.taskDueDate = Date.from(task.getTaskDueDate().toInstant());
        this.taskRewardPoints = task.getTaskRewardPoints();
        this.taskId = task.getId();
    }

    public CreateTaskDTO() {
        super();
    }

    // Getter ve Setter metotları
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

    public OffsetDateTime getTaskDueDate() {
        // convert string to OffsetDateTime
        
        return OffsetDateTime.ofInstant(taskDueDate.toInstant(), ZoneOffset.UTC);
    }

    public void setTaskDueDate(Date taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    public int getTaskRewardPoints() {
        return taskRewardPoints;
    }

    public void setTaskRewardPoints(int taskRewardPoints) {
        this.taskRewardPoints = taskRewardPoints;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
 

}
