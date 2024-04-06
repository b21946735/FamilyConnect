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
}