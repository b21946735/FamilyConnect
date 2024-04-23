package com.familyconnect.fc.task;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;

import javax.print.DocFlavor.STRING;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.familyconnect.fc.family.Family;
import com.familyconnect.fc.family.FamilyRepository;
import com.familyconnect.fc.progress.Progress;
import com.familyconnect.fc.progress.ProgressService;
import com.familyconnect.fc.user.ApplicationUser;
import com.familyconnect.fc.user.UserRepository;
import com.familyconnect.fc.utils.Enums.TaskStatus;


@Service
@Transactional
public class TaskService {

    @Autowired
    private  FamilyRepository familyRepository;

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProgressService progressService;


    public Task addTask(CreateTaskDTO task) {
        // add task to family then return success message or error message
        System.out.println(task.getTaskCreatorUserName() + " task added");
        ApplicationUser taskCreator = userRepository.findByUsername(task.getTaskCreatorUserName()).get();
        ApplicationUser taskAssignee = userRepository.findByUsername(task.getTaskAssigneeUserName()).get();

        Optional<Family> familyOptional = familyRepository.findById(taskCreator.getFamilyId());
        if(!familyOptional.isPresent()){
            return null;
        }
        Family family = familyOptional.get();
       
        Task newTask = new Task(task, family);
        if(taskCreator == null || taskAssignee == null){
            return null;
        }
        family.addTask(newTask);
        familyRepository.save(family);

        return newTask;
    }
        
    public List<Task> getTasks(String username){
        if(!userRepository.findByUsername(username).isPresent()){
            return null;
        }
        ApplicationUser user = userRepository.findByUsername(username).get();
        Optional <Family> familyOptional = familyRepository.findById(user.getFamilyId());
        if(!familyOptional.isPresent()){
            return null;
        }
        Family family = familyOptional.get();

        List<Task> tasks = family.getTasks();   
        List<Task> userTasks = new ArrayList<Task>();
        System.out.println("tasks size: " + tasks.size());
        for(Task task : tasks){
            System.out.println(task.getTaskAssigneeUserName());
            if(task.getTaskAssigneeUserName().equals(username)){
                userTasks.add(task);
                System.out.println(task.getTaskName());
            }
        }
        return userTasks;
    }

    public Task completeTask(Integer taskId){
        Optional<Task> task = taskRepository.findById(taskId);
        if(!task.isPresent()){
            return null;
        }
        Task completedTask = task.get();
        completedTask.setTaskStatus(TaskStatus.COMPLETED);
        taskRepository.save(completedTask);
        
        List<Progress> progressList = progressService.getProgressByUserName(completedTask.getTaskAssigneeUserName());
        OffsetDateTime now = OffsetDateTime.now();
        
        // check if any progress time has not passed yet and update the progress 
        for(Progress progress : progressList){
            if(progress.getDueDate().isAfter(now)){
                progress.setCurrentStatus(progress.getCurrentStatus() + completedTask.getTaskRewardPoints());
                progressService.updateProgress(progress.getId(), progress);
            }
        }



        return completedTask;
    }

    public Task updateTask(Integer taskId, CreateTaskDTO task){
        Optional<Task> taskToUpdate = taskRepository.findById(taskId);
        if(!taskToUpdate.isPresent()){
            return null;
        }
        Task updatedTask = taskToUpdate.get();
        updatedTask.setTaskName(task.getTaskName());
        updatedTask.setTaskDescription(task.getTaskDescription());
        updatedTask.setTaskRewardPoints(task.getTaskRewardPoints());
        updatedTask.setTaskDueDate(task.getTaskDueDate());
        taskRepository.save(updatedTask);
        return updatedTask;
    }

    public Task deleteTask(String userName, Integer taskId){
        Optional<Task> taskToDelete = taskRepository.findById(taskId);
        if(!taskToDelete.isPresent()){
            return null;
        }
        Task deletedTask = taskToDelete.get();
        //check if task belongs to the same family
        ApplicationUser user = userRepository.findByUsername(userName).get();
        Optional <Family> familyOptional = familyRepository.findById(user.getFamilyId());
        if(!familyOptional.isPresent()){
            return null;
        }
        Family family = familyOptional.get();
        if(!family.getTasks().contains(deletedTask)){
            return null;
        }
        family.getTasks().remove(deletedTask);
        familyRepository.save(family);

        taskRepository.delete(deletedTask);
        return deletedTask;
    }

} 