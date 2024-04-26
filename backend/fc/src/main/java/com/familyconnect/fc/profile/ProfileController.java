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
            ApplicationUser updatedProfile = profileService.updateProfileName(username, name);
            if(updatedProfile == null){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Profile name could not be updated");
            }
            return ResponseEntity.ok().body("Profile name updated successfully");
        }

        // update profile password
        @PutMapping("/updatePassword/{username}/{oldPassword}/{password}")
        public ResponseEntity updateProfilePassword(@PathVariable String username,@PathVariable String oldPassword , @PathVariable String password){
            ApplicationUser updatedProfile = profileService.updateProfilePassword(username,oldPassword, password);
            if(updatedProfile == null){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Profile password could not be updated");
            }
            return ResponseEntity.ok().body("Profile password updated successfully");
        }

        //update profile picture id
        @PutMapping("/updateProfilePicture/{username}/{profilePictureId}")
        public ResponseEntity updateProfilePicture(@PathVariable String username, @PathVariable int profilePictureId){
            ApplicationUser updatedProfile = profileService.updateProfilePicture(username, profilePictureId);
            if(updatedProfile == null){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Profile picture could not be updated");
            }
            return ResponseEntity.ok().body("Profile picture updated successfully");
        }
}
