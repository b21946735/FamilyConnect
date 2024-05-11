package com.familyconnect.fc.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.familyconnect.fc.family.FamilyService;


@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class ParentController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUser/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username){
        ApplicationUser user = userService.getUser(username);
        if(user == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found.");
        }

        return ResponseEntity.ok().body(user);
    }
    
}
