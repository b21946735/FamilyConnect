package com.familyconnect.fc.family;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/family")
public class FamilyController {

    @Autowired
    private FamilyService familyService;


    @PostMapping("/createFamily")
    public ResponseEntity<?> createFamily(@RequestBody FamilyRequestDTO family) {
        ResponseEntity<?> createdfamily = familyService.Createfamily(family);
        return ResponseEntity.status(createdfamily.getStatusCode()).body(createdfamily.getBody());
    }

    
    @GetMapping("/getFamily")
    public ResponseEntity<?> getFamily(@RequestParam String userName){
        ResponseEntity<?> family = familyService.GetFamily(userName);
        return ResponseEntity.status(family.getStatusCode()).body(family.getBody());
    }

    // get family member user infos
    @GetMapping("/getFamilyMembersInformation")
    public ResponseEntity<?> getFamilyMembersInformation(@RequestParam String userName){
        ResponseEntity<?> familyMembers = familyService.getFamilyMembersInformation(userName);
        return ResponseEntity.status(familyMembers.getStatusCode()).body(familyMembers.getBody());
    }

    @PostMapping("/addFamilyMember")
    public ResponseEntity<?> addFamilyMembers(@RequestParam("familyId") Integer familyId, @RequestBody List<String> userNames){
        ResponseEntity<?> family = familyService.addFamilyMembers(familyId, userNames);
        return ResponseEntity.status(family.getStatusCode()).body(family.getBody());
    }

    @DeleteMapping("/removeFamilyMember")
    public ResponseEntity<?> removeFamilyMember(@RequestParam("familyId") Integer familyId, @RequestParam("userName") String userName){
        ResponseEntity<?> family = familyService.removeFamilyMember(familyId, userName);
        return ResponseEntity.status(family.getStatusCode()).body(family.getBody());
    }

    //change family name
    @PutMapping("/updateFamilyName")
    public ResponseEntity<?> updateFamilyName(@RequestParam("username") String username, @RequestParam("familyName") String familyName){
        ResponseEntity<?> family = familyService.updateFamilyName(username, familyName);
        return ResponseEntity.status(family.getStatusCode()).body(family.getBody());
    }

    // get users spins
    @GetMapping("/getSpins")
    public ResponseEntity<?> getSpins(@RequestParam("username") String username){
        ResponseEntity<?> spins = familyService.getSpins(username);
        return ResponseEntity.status(spins.getStatusCode()).body(spins.getBody());
    }

    // get specific user spins
    @GetMapping("/getUserSpins")
    public ResponseEntity<?> getUserSpins(@RequestParam("username") String username){
        ResponseEntity<?> spins = familyService.getUserSpins(username);
        return ResponseEntity.status(spins.getStatusCode()).body(spins.getBody());
    }

    // roll spin
    @PostMapping("/setReward")
    public ResponseEntity<?> setReward(@RequestBody FamilySpinDataDTO spinData){
        ResponseEntity<?> spin = familyService.setReward(spinData);
        return ResponseEntity.status(spin.getStatusCode()).body(spin.getBody());
    }

    // get family rewards
    @GetMapping("/getFamilyRewards")
    public ResponseEntity<?> getFamilyRewards(@RequestParam("username") String username){
        ResponseEntity<?> rewards = familyService.getFamilyRewards(username);
        return ResponseEntity.status(rewards.getStatusCode()).body(rewards.getBody());
    }

    // get user rewards
    @GetMapping("/getUserRewards")
    public ResponseEntity<?> getUserRewards(@RequestParam("username") String username){
        ResponseEntity<?> rewards = familyService.getUserRewards(username);
        return ResponseEntity.status(rewards.getStatusCode()).body(rewards.getBody());
    }

    // get family photos
    @GetMapping("/getFamilyPhotos")
    public ResponseEntity<?> getFamilyPhotos(@RequestParam("username") String username){
        ResponseEntity<?> photos = familyService.getFamilyPhotos(username);
        return ResponseEntity.status(photos.getStatusCode()).body(photos.getBody());
    }

    // add family photos
    @PostMapping("/addFamilyPhotos")
    public ResponseEntity<?> addFamilyPhotos( @RequestBody FamilyPhotosDTO photos){
        ResponseEntity<?> newphotos = familyService.addFamilyPhotos( photos);
        return ResponseEntity.status(newphotos.getStatusCode()).body(newphotos.getBody());
    }

    // set family photos
    @PutMapping("/setFamilyPhotos")
    public ResponseEntity<?> setFamilyPhotos(@RequestBody FamilyPhotosDTO photos){
        ResponseEntity<?> newphotos = familyService.setFamilyPhotos( photos);
        return ResponseEntity.status(newphotos.getStatusCode()).body(newphotos.getBody());
    }


}