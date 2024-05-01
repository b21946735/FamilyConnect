package com.familyconnect.fc.utils;

public class Enums {
    public enum UserRole {
        ADMIN, 
        PARENT,
        CHILD
    }

    public enum TaskStatus {
        IN_PROGRESS, 
        COMPLETED, 
        FAILED,
        PENDING
    }

    public enum ProgressStatus {
        IN_PROGRESS, 
        COMPLETED, 
        CANCELLED
    }
}
