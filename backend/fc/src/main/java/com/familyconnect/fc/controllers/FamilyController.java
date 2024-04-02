package com.familyconnect.fc.controllers;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.familyconnect.fc.models.Family;
import com.familyconnect.fc.models.FamilyRequestDTO;
import com.familyconnect.fc.services.FamilyService;

@RestController
@RequestMapping("/family")
public class FamilyController {

    @Autowired
    private FamilyService familyService;


    @PostMapping("/createFamily")
    public ResponseEntity createFamily(@RequestBody FamilyRequestDTO family) {
        Family createdfamily = familyService.Createfamily(family);
        if (createdfamily == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found or already belongs to a family");
        }
        return ResponseEntity.ok(createdfamily);
    }

    
    @GetMapping("/getFamily")
    public ResponseEntity getFamily(@RequestParam String userName){
        Family family = familyService.GetFamily(userName);
        if(family == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found or does not belong to a family");
        }
        return ResponseEntity.ok(family);
    }
}