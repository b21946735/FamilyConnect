package com.familyconnect.fc.task;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;


    @PostMapping("/addTask")
    public ResponseEntity addTask(@RequestBody CreateTaskDTO task){
        // add task to family then return success message or error message
        Task newTask = taskService.addTask(task);
        if(newTask == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Task could not be added");
        }
        return ResponseEntity.ok().body("Task added successfully");
    }

    @GetMapping("/getTasks/{username}")
    public ResponseEntity getTasks(@PathVariable String username){
        List<Task> tasks = taskService.getTasks(username);
        if(tasks == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tasks found");
        }
        return ResponseEntity.ok().body(tasks);
    }

    @PostMapping("/completeTask/{username}/{taskId}")
    public ResponseEntity completeTask(@PathVariable String username, @PathVariable Integer taskId){
        Task task = taskService.completeTask(username,taskId);
        if(task == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Task could not be completed or not exist");
        }
        return ResponseEntity.ok().body("Task completed successfully");
    }

    // pending task
    @PostMapping("/pendingTask/{username}/{taskId}")
    public ResponseEntity pendingTask(@PathVariable String username, @PathVariable Integer taskId){
        Task task = taskService.pendingTask(username,taskId);
        if(task == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Task could not be pending or not exist");
        }
        return ResponseEntity.ok().body("Task pending successfully");
    }

    // reject task
    @PostMapping("/rejectTask/{username}/{taskId}")
    public ResponseEntity rejectTask(@PathVariable String username, @PathVariable Integer taskId){
        Task task = taskService.rejectTask(username,taskId);
        if(task == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Task could not be rejected or not exist");
        }
        return ResponseEntity.ok().body("Task rejected successfully");
    }

    // get pending tasks
    @GetMapping("/getPendingTasks/{username}")
    public ResponseEntity getPendingTasks(@PathVariable String username){
        List<Task> tasks = taskService.getPendingTasks(username);
        if(tasks == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tasks found");
        }
        return ResponseEntity.ok().body(tasks);
    }

    @PutMapping("/updateTask/{taskId}")
    public ResponseEntity updateTask(@PathVariable Integer taskId, @RequestBody CreateTaskDTO task){
        Task updatedTask = taskService.updateTask(taskId, task);
        if(updatedTask == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Task could not be updated or not exist");
        }
        return ResponseEntity.ok().body("Task updated successfully");
    }

    @DeleteMapping("/deleteTask/{userName}/{taskId}")
    public ResponseEntity deleteTask(@PathVariable String userName, @PathVariable Integer taskId){
        Task task = taskService.deleteTask(userName, taskId);
        if(task == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Task could not be deleted or not exist");
        }
        return ResponseEntity.ok().body("Task deleted successfully");
    }
}