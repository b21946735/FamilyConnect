package com.familyconnect.fc.user;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class ChildController {

    @GetMapping("/")
    public String helloChildController(){
        return "Child access level";
    }
    
}
