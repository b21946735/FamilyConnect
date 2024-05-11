package com.familyconnect.fc.profile;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.familyconnect.fc.authentication.AuthenticationService;

import com.familyconnect.fc.user.ApplicationUser;
import com.familyconnect.fc.user.UserRepository;


@Service
@Transactional
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    public ResponseEntity<?> updateProfileName(String username, String name) {
        Optional<ApplicationUser> userOptional = userRepository.findByUsername(username);
        if(!userOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        ApplicationUser user = userOptional.get();
        user.setName(name);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    public ResponseEntity<?> updateProfilePassword(String username,String oldPassword ,String password) {
        Optional<ApplicationUser> userOptional = userRepository.findByUsername(username);
        if(!userOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        
        authenticationService.changePassword(username, oldPassword, password);
        ApplicationUser user = userOptional.get();
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    public ResponseEntity<?> updateProfilePictureOld(String username, int profilePictureId) {
        Optional<ApplicationUser> userOptional = userRepository.findByUsername(username);
        if(!userOptional.isPresent()){  
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        ApplicationUser user = userOptional.get();
        user.setProfilePictureId(profilePictureId);
        userRepository.save(user);

        // 
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    public ResponseEntity<?> updateProfilePictureUrl(UpdateProfilePictureUrlDTO updateProfilePictureUrlDTO) {
        Optional<ApplicationUser> userOptional = userRepository.findByUsername(updateProfilePictureUrlDTO.getUsername());
        if(!userOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        ApplicationUser user = userOptional.get();
        user.setProfilePictureUrl(updateProfilePictureUrlDTO.getProfilePictureUrl());
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
