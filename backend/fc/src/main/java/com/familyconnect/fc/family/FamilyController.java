package com.familyconnect.fc.family;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.familyconnect.fc.family.FamilyRequestDTO;

@RestController
@RequestMapping("/family")
public class FamilyController {

    @Autowired
    private FamilyService familyService;


    @PostMapping("/createFamily")
    public ResponseEntity createFamily(@RequestBody FamilyRequestDTO family) {
        ResponseEntity createdfamily = familyService.Createfamily(family);
        return ResponseEntity.status(createdfamily.getStatusCode()).body(createdfamily.getBody());
    }

    
    @GetMapping("/getFamily")
    public ResponseEntity getFamily(@RequestParam String userName){
        ResponseEntity family = familyService.GetFamily(userName);
        return ResponseEntity.status(family.getStatusCode()).body(family.getBody());
    }

    // get family member user infos
    @GetMapping("/getFamilyMembersInformation")
    public ResponseEntity getFamilyMembersInformation(@RequestParam String userName){
        ResponseEntity familyMembers = familyService.getFamilyMembersInformation(userName);
        return ResponseEntity.status(familyMembers.getStatusCode()).body(familyMembers.getBody());
    }

    @PostMapping("/addFamilyMember")
    public ResponseEntity addFamilyMembers(@RequestParam("familyId") Integer familyId, @RequestBody List<String> userNames){
        ResponseEntity family = familyService.addFamilyMembers(familyId, userNames);
        return ResponseEntity.status(family.getStatusCode()).body(family.getBody());
    }

    @DeleteMapping("/removeFamilyMember")
    public ResponseEntity removeFamilyMember(@RequestParam("familyId") Integer familyId, @RequestParam("userName") String userName){
        ResponseEntity family = familyService.removeFamilyMember(familyId, userName);
        return ResponseEntity.status(family.getStatusCode()).body(family.getBody());
    }

    //change family name
    @PutMapping("/updateFamilyName")
    public ResponseEntity updateFamilyName(@RequestParam("username") String username, @RequestParam("familyName") String familyName){
        ResponseEntity family = familyService.updateFamilyName(username, familyName);
        return ResponseEntity.status(family.getStatusCode()).body(family.getBody());
    }

}