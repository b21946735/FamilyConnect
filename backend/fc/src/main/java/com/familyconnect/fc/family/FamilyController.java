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

    // get family member user infos
    @GetMapping("/getFamilyMembersInformation")
    public ResponseEntity getFamilyMembersInformation(@RequestParam String userName){
        List<FamilyMemberInfoDTO> familyMembers = familyService.getFamilyMembersInformation(userName);
        if(familyMembers == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Family not found");
        }
        return ResponseEntity.ok(familyMembers);
    }

    @PostMapping("/addFamilyMember")
    public ResponseEntity addFamilyMembers(@RequestParam("familyId") Integer familyId, @RequestBody List<String> userNames){
        Family family = familyService.addFamilyMembers(familyId, userNames);
        if(family == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found or already belongs to a family");
        }
        return ResponseEntity.ok(family);
    }

    //change family name
    @PutMapping("/updateFamilyName")
    public ResponseEntity updateFamilyName(@RequestParam("username") String username, @RequestParam("familyName") String familyName){
        Family family = familyService.updateFamilyName(username, familyName);
        if(family == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Family not found");
        }
        return ResponseEntity.ok(family);
    }

}