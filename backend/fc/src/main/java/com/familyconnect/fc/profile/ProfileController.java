package com.familyconnect.fc.profile;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.familyconnect.fc.user.ApplicationUser;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    
        @Autowired
        private ProfileService profileService;
    
        // update profile name
        @PutMapping("/updateName/{username}/{name}")
        public ResponseEntity updateProfileName(@PathVariable String username, @PathVariable String name){
            ResponseEntity updatedProfile = profileService.updateProfileName(username, name);
            return ResponseEntity.status(updatedProfile.getStatusCode()).body(updatedProfile.getBody());
        }

        // update profile password
        @PutMapping("/updatePassword/{username}/{oldPassword}/{password}")
        public ResponseEntity updateProfilePassword(@PathVariable String username,@PathVariable String oldPassword , @PathVariable String password){
            ResponseEntity updatedProfile = profileService.updateProfilePassword(username,oldPassword, password);
            return ResponseEntity.status(updatedProfile.getStatusCode()).body(updatedProfile.getBody());
        }

        //update profile picture id
        @PutMapping("/updateProfilePicture/{username}/{profilePictureId}")
        public ResponseEntity updateProfilePicture(@PathVariable String username, @PathVariable int profilePictureId){
            ResponseEntity updatedProfile = profileService.updateProfilePicture(username, profilePictureId);
            return ResponseEntity.status(updatedProfile.getStatusCode()).body(updatedProfile.getBody());
        }
}
