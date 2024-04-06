package com.familyconnect.fc.family;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.familyconnect.fc.user.ApplicationUser;
import com.familyconnect.fc.user.UserRepository;
import com.familyconnect.fc.utils.Enums.UserRole;
import com.familyconnect.fc.family.FamilyRequestDTO;


@Service
@Transactional
public class FamilyService {

    @Autowired
    private  FamilyRepository familyRepository;

    @Autowired
    private  UserRepository userRepository;

    public Family Createfamily(FamilyRequestDTO family) {
        Family myFamily = new Family(family.getFamilyName(), family.getFamilyCreatorUserName());
        
        String creatorName = family.getFamilyCreatorUserName();
        
        System.out.println("creator name: " + creatorName );
        if(userRepository.findByUsername(creatorName).isPresent()){
            ApplicationUser user = userRepository.findByUsername(creatorName).get();
            if(user.getFamilyId() != null && user.getFamilyId() != -1){
                System.out.println("User already belongs to a family");
                return null;
            }

            if(user.isChild()){
                System.out.println("User is a child and cannot create a family");
                return null;
            }
            

            familyRepository.save(myFamily);
            
            Integer familyId = myFamily.getId();
            user.setFamilyId(familyId);
            userRepository.save(user);
            System.out.println("Family id: " + familyId);
            System.out.println("Family created successfully for user: " + creatorName);
            return myFamily;
        }
        System.out.println("User not found while creating family");
        return null;
  
    }

    public Family GetFamily(String userName) {
        // check if user exists
        if(!userRepository.findByUsername(userName).isPresent()){
            System.out.println("User not found while getting family");
            return null;
        }

        Integer userFamilyId = userRepository.findByUsername(userName).get().getFamilyId();
        if(userFamilyId == null || userFamilyId == -1){
            System.out.println("User does not belong to any family");
            return null;
        }
        Family family = familyRepository.findById(userFamilyId).get();
        System.out.println("Family found: " + family.getFamilyName());
        return family;
    }

    public Family addFamilyMembers(int familyId, List<String> userNames) {
        Optional<Family> familyOpt = familyRepository.findById(familyId);
        if (!familyOpt.isPresent()) {
            System.out.println("Family not found with ID: " + familyId);
            return null;
        }

        Family family = familyOpt.get();
        List<ApplicationUser> usersToAdd = new ArrayList<>();

        for (String userName : userNames) {
            Optional<ApplicationUser> userOpt = userRepository.findByUsername(userName);
            if (!userOpt.isPresent() || (userOpt.get().getFamilyId() != null && userOpt.get().getFamilyId() != -1)) {
                System.out.println("User not found or already belongs to a family: " + userName);
                return null; 
            }
            ApplicationUser user = userOpt.get();
            user.setFamilyId(familyId);
            usersToAdd.add(user);
        }
        
        userRepository.saveAll(usersToAdd);
        return family;
    }
        

} 