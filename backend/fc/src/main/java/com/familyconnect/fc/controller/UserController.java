package com.familyconnect.fc.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    
        @GetMapping("/")
        public String helloUserController() {
            System.out.println("Hello World from UserController");
            return "User Access Level";
        }
}
