package com.familyconnect.fc.user;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parent")
@CrossOrigin("*")
public class ParentController {

    @GetMapping("/")
    public String helloParentController(){
        return "Parent access level";
    }
    
}
