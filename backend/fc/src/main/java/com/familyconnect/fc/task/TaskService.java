package com.familyconnect.fc.task;

import java.util.ArrayList;

import javax.print.DocFlavor.STRING;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.familyconnect.fc.family.Family;
import com.familyconnect.fc.family.FamilyRepository;
import com.familyconnect.fc.user.ApplicationUser;
import com.familyconnect.fc.user.UserRepository;


@Service
@Transactional
public class TaskService {

    @Autowired
    private  FamilyRepository familyRepository;

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;


    public Task addTask(CreateTaskDTO task) {
        // add task to family then return success message or error message
        
        ApplicationUser taskCreator = userRepository.findByUsername(task.getTaskCreatorUserName()).get();
        ApplicationUser taskAssignee = userRepository.findByUsername(task.getTaskAssigneeUserName()).get();
        Family family = familyRepository.findById(taskCreator.getFamilyId()).get();
        Task newTask = new Task(task, family);
        if(taskCreator == null || taskAssignee == null){
            return null;
        }

        family.addTask(newTask);
        familyRepository.save(family);

        userRepository.save(taskAssignee);

        taskRepository.save(newTask);

        

        return newTask;
    }
        
    public List<Task> getTasks(String username) {
        ApplicationUser user = userRepository.findByUsername(username).get();
        Family family = familyRepository.findById(user.getFamilyId()).get();
        List<Task> tasks = family.getTasks();
        return tasks;
    }

} 