package com.familyconnect.fc.profile;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;

import javax.print.DocFlavor.STRING;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.familyconnect.fc.authentication.AuthenticationService;
import com.familyconnect.fc.family.Family;
import com.familyconnect.fc.family.FamilyRepository;
import com.familyconnect.fc.progress.Progress;
import com.familyconnect.fc.progress.ProgressService;
import com.familyconnect.fc.user.ApplicationUser;
import com.familyconnect.fc.user.UserRepository;
import com.familyconnect.fc.utils.Enums.TaskStatus;


@Service
@Transactional
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    public ApplicationUser updateProfileName(String username, String name) {
        Optional<ApplicationUser> userOptional = userRepository.findByUsername(username);
        if(!userOptional.isPresent()){
            return null;
        }
        ApplicationUser user = userOptional.get();
        user.setName(name);
        userRepository.save(user);
        return user;
    }

    public ApplicationUser updateProfilePassword(String username,String oldPassword ,String password) {
        Optional<ApplicationUser> userOptional = userRepository.findByUsername(username);
        if(!userOptional.isPresent()){
            return null;
        }
        
        authenticationService.changePassword(username, oldPassword, password);
        ApplicationUser user = userOptional.get();
        return user;
    }

    public ApplicationUser updateProfilePicture(String username, int profilePictureId) {
        Optional<ApplicationUser> userOptional = userRepository.findByUsername(username);
        if(!userOptional.isPresent()){  
            return null;
        }
        ApplicationUser user = userOptional.get();
        user.setProfilePictureId(profilePictureId);
        userRepository.save(user);

        // 
        return user;
    }

}
