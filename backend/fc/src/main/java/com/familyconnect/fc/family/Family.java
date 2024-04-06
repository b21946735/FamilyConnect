package com.familyconnect.fc.family;



import java.util.ArrayList;
import java.util.List;

import com.familyconnect.fc.task.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String familyName;
    private List<String> familyMembers = new ArrayList<String>();
    private String creatorUserName;

    @JsonIgnore
    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

  
    public Family(String familyName, String creatorUserName) {
		super();
		this.familyName = familyName;
        this.creatorUserName = creatorUserName;
        this.familyMembers.add(creatorUserName);
	}

    public Family() {
        super();
    }

    // Getter ve Setter metotları
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFamilyName() { 
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<String> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(List<String> familyMembers) {
        this.familyMembers = familyMembers;
    }

    public String getCreatorUserName() {
        return creatorUserName;
    }

    public void setCreatorUserName(String creatorUserName) {
        this.creatorUserName = creatorUserName;
    }

    public void addTask(Task task) {
        System.out.println("Task added to family " + this.familyName + " tasks size from " + this.tasks.size() + " to " + (this.tasks.size() + 1));
        this.tasks.add(task);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
    }
}
