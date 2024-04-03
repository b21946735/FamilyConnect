package com.familyconnect.fc.models;


public class CreateTaskDTO {
    private String taskName;
    private String taskDescription;
    private String taskCreatorUserName;
    
    private String taskAssigneeUserName;
    private String taskDueDate; // format: yyyy-MM-dd

    private int taskRewardPoints;

    private int familyId;

    public CreateTaskDTO(String taskName, String taskDescription, String taskCreatorUserName, String taskAssigneeUserName, String taskDueDate, int taskRewardPoints, int familyId) {
        super();
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskCreatorUserName = taskCreatorUserName;
        this.taskAssigneeUserName = taskAssigneeUserName;
        this.taskDueDate = taskDueDate;
        this.taskRewardPoints = taskRewardPoints;
        this.familyId = familyId;
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

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

}