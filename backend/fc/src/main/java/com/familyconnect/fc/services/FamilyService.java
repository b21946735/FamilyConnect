package com.familyconnect.fc.services;

import javax.print.DocFlavor.STRING;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.familyconnect.fc.models.ApplicationUser;
import com.familyconnect.fc.models.Family;
import com.familyconnect.fc.models.FamilyRequestDTO;
import com.familyconnect.fc.repository.FamilyRepository;
import com.familyconnect.fc.repository.UserRepository;


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
}