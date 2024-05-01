package com.familyconnect.fc.task;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    public ResponseEntity<?> addTask(CreateTaskDTO task) {
        // add task to family then return success message or error message but same day due date is allowed
        
        if(task.getTaskDueDate().withHour(23).withMinute(59).withSecond(59).isBefore(OffsetDateTime.now())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Due date cannot be before request date");
        }
        ApplicationUser taskCreator = userRepository.findByUsername(task.getTaskCreatorUserName()).get();
        ApplicationUser taskAssignee = userRepository.findByUsername(task.getTaskAssigneeUserName()).get();

        Optional<Family> familyOptional = familyRepository.findById(taskCreator.getFamilyId());
        if(!familyOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Family not found");
        }
        Family family = familyOptional.get();
       
        Task newTask = new Task(task, family);
        if(taskCreator == null || taskAssignee == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // if due date is before request date return null
        

        System.out.println(task.getTaskCreatorUserName() + " task added");
        family.addTask(newTask);
        familyRepository.save(family);

        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }
        
    public ResponseEntity<?> getTasks(String username){
        if(!userRepository.findByUsername(username).isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        ApplicationUser user = userRepository.findByUsername(username).get();
        Optional <Family> familyOptional = familyRepository.findById(user.getFamilyId());
        if(!familyOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Family not found");
        }
        Family family = familyOptional.get();

        List<Task> tasks = family.getTasks();   
        List<Task> userTasks = new ArrayList<Task>();
        for(Task task : tasks){
            if(task.getTaskAssigneeUserName().equals(username)){
                userTasks.add(task);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(userTasks);
    }

    
    public ResponseEntity<?> pendingTask(String username, Integer taskId){
        Optional<Task> task = taskRepository.findById(taskId);
        if(!task.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
        Task pendingTask = task.get();

        if(pendingTask.getTaskStatus() != TaskStatus.IN_PROGRESS){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task is not in progress");
        }

        //check if task belongs to the same family
        ApplicationUser user = userRepository.findByUsername(username).get();
        Optional <Family> familyOptional = familyRepository.findById(user.getFamilyId());
        if(!familyOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Family not found");
        }

        Family family = familyOptional.get();

        if(!family.getTasks().contains(pendingTask)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task does not belong to the family");
        }

        pendingTask.setTaskStatus(TaskStatus.PENDING);

        taskRepository.save(pendingTask);

        return ResponseEntity.status(HttpStatus.OK).body(pendingTask);
    }

    // reject pending task
    public ResponseEntity<?> rejectTask(String username, Integer taskId){
        Optional<Task> task = taskRepository.findById(taskId);
        if(!task.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
        Task rejectedTask = task.get();

        if(rejectedTask.getTaskStatus() != TaskStatus.PENDING && rejectedTask.getTaskStatus() != TaskStatus.IN_PROGRESS){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task is not pending or in progress");
        }

        //check if task belongs to the same family
        ApplicationUser user = userRepository.findByUsername(username).get();
        Optional <Family> familyOptional = familyRepository.findById(user.getFamilyId());
        if(!familyOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Family not found");
        }

        Family family = familyOptional.get();

        if(!family.getTasks().contains(rejectedTask)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task does not belong to the family");    
        }

        // check if task time is not passed if it is passed set task status to failed else set it to in progress
        OffsetDateTime now = OffsetDateTime.now();
        if(rejectedTask.getTaskDueDate().withHour(23).isBefore(now)){
            rejectedTask.setTaskStatus(TaskStatus.FAILED);
        }
        else{
            rejectedTask.setTaskStatus(TaskStatus.IN_PROGRESS);
        }


        
        taskRepository.save(rejectedTask);

        return ResponseEntity.status(HttpStatus.OK).body(rejectedTask);
    }


    public ResponseEntity<?> completeTask(String username, Integer taskId){
        Optional<Task> task = taskRepository.findById(taskId);
        if(!task.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
        Task completedTask = task.get();

        //check if task belongs to the same family
        ApplicationUser user = userRepository.findByUsername(username).get();
        Optional <Family> familyOptional = familyRepository.findById(user.getFamilyId());
        if(!familyOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Family not found");
        }
        Family family = familyOptional.get();
        if(!family.getTasks().contains(completedTask)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task does not belong to the family");
        }

        // check if user is parent
        if(!user.isParent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is not parent");
        }

        // check if task is in progress
        if(completedTask.getTaskStatus() != TaskStatus.IN_PROGRESS){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task is not in progress");
        }


        completedTask.setTaskStatus(TaskStatus.COMPLETED);
        taskRepository.save(completedTask);
        
        List<Progress> progressList = progressService.getProgressByUserName(completedTask.getTaskAssigneeUserName());
        OffsetDateTime now = OffsetDateTime.now();
        
        // check if any progress time has not passed yet and update the progress 
        for(Progress progress : progressList){
            if(progress.getDueDate().withHour(0).isAfter(now)){
                progress.setCurrentStatus(progress.getCurrentStatus() + completedTask.getTaskRewardPoints());
                progressService.updateProgress(progress.getId(), progress);
            }
        }



        return ResponseEntity.status(HttpStatus.OK).body(completedTask);
    }

    public ResponseEntity<?> updateTask(Integer taskId, CreateTaskDTO task){
        Optional<Task> taskToUpdate = taskRepository.findById(taskId);
        if(!taskToUpdate.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
        Task updatedTask = taskToUpdate.get();
        updatedTask.setTaskName(task.getTaskName());
        updatedTask.setTaskDescription(task.getTaskDescription());
        updatedTask.setTaskRewardPoints(task.getTaskRewardPoints());
        updatedTask.setTaskDueDate(task.getTaskDueDate());
        taskRepository.save(updatedTask);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
    }

    public ResponseEntity<?> deleteTask(String userName, Integer taskId){
        Optional<Task> taskToDelete = taskRepository.findById(taskId);
        if(!taskToDelete.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
        Task deletedTask = taskToDelete.get();
        //check if task belongs to the same family
        ApplicationUser user = userRepository.findByUsername(userName).get();
        Optional <Family> familyOptional = familyRepository.findById(user.getFamilyId());
        if(!familyOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Family not found");
        }
        Family family = familyOptional.get();
        if(!family.getTasks().contains(deletedTask)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task does not belong to the family");
        }
        family.getTasks().remove(deletedTask);
        familyRepository.save(family);

        taskRepository.delete(deletedTask);
        return ResponseEntity.status(HttpStatus.OK).body("Task deleted successfully");
    }

    public ResponseEntity<?> getPendingTasks(String username) {
        if(!userRepository.findByUsername(username).isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        ApplicationUser user = userRepository.findByUsername(username).get();

        Optional <Family> familyOptional = familyRepository.findById(user.getFamilyId());
        if(!familyOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Family not found");
        }

        Family family = familyOptional.get();

        List<Task> tasks = family.getTasks();

        List<Task> pendingTasks = new ArrayList<Task>();

        for(Task task : tasks){
            if(task.getTaskStatus() == TaskStatus.PENDING){
                pendingTasks.add(task);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(pendingTasks);
    }

    public ResponseEntity<?> getAllTasks(String username) {
        if(!userRepository.findByUsername(username).isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        ApplicationUser user = userRepository.findByUsername(username).get();

        Optional <Family> familyOptional = familyRepository.findById(user.getFamilyId());

        if(!familyOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Family not found");
        }


        Family family = familyOptional.get();

        List<Task> tasks = family.getTasks();

        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

} 