package com.familyconnect.fc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Family> createFamily(@RequestBody FamilyRequestDTO family) {
        Family createdfamily = familyService.Createfamily(family);
        return ResponseEntity.ok(createdfamily);
    }
}