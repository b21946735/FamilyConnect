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
        ResponseEntity newTask = taskService.addTask(task);
        return ResponseEntity.status(newTask.getStatusCode()).body(newTask.getBody());
    }

    @GetMapping("/getTasks/{username}")
    public ResponseEntity getTasks(@PathVariable String username){
        ResponseEntity tasks = taskService.getTasks(username);
        return ResponseEntity.status(tasks.getStatusCode()).body(tasks.getBody());
    }

    // get all tasks
    @GetMapping("/getAllTasks/{username}")
    public ResponseEntity getAllTasks(@PathVariable String username){
        ResponseEntity tasks = taskService.getAllTasks(username);
        return ResponseEntity.status(tasks.getStatusCode()).body(tasks.getBody());
    }
    

    @PostMapping("/completeTask/{username}/{taskId}")
    public ResponseEntity completeTask(@PathVariable String username, @PathVariable Integer taskId){
        ResponseEntity task = taskService.completeTask(username,taskId);
        return ResponseEntity.status(task.getStatusCode()).body(task.getBody());
    }

    // pending task
    @PostMapping("/pendingTask/{username}/{taskId}")
    public ResponseEntity pendingTask(@PathVariable String username, @PathVariable Integer taskId){
        ResponseEntity task = taskService.pendingTask(username,taskId);
        return ResponseEntity.status(task.getStatusCode()).body(task.getBody());
    }

    // reject task
    @PostMapping("/rejectTask/{username}/{taskId}")
    public ResponseEntity rejectTask(@PathVariable String username, @PathVariable Integer taskId){
        ResponseEntity task = taskService.rejectTask(username,taskId);
        return ResponseEntity.status(task.getStatusCode()).body(task.getBody());
    }

    // get pending tasks
    @GetMapping("/getPendingTasks/{username}")
    public ResponseEntity getPendingTasks(@PathVariable String username){
        ResponseEntity tasks = taskService.getPendingTasks(username);
        return ResponseEntity.status(tasks.getStatusCode()).body(tasks.getBody());
    }

    @PutMapping("/updateTask/{taskId}")
    public ResponseEntity updateTask(@PathVariable Integer taskId, @RequestBody CreateTaskDTO task){
        ResponseEntity updatedTask = taskService.updateTask(taskId, task);
        return ResponseEntity.status(updatedTask.getStatusCode()).body(updatedTask.getBody());
    }

    @DeleteMapping("/deleteTask/{userName}/{taskId}")
    public ResponseEntity deleteTask(@PathVariable String userName, @PathVariable Integer taskId){
        ResponseEntity task = taskService.deleteTask(userName, taskId);
        return ResponseEntity.status(task.getStatusCode()).body(task.getBody());
    }
}