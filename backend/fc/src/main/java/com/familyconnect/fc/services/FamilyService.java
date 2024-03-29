package com.familyconnect.fc.services;

import javax.print.DocFlavor.STRING;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        familyRepository.save(myFamily);
        userRepository.findByUsername(family.getFamilyCreatorUserName()).get().setFamilyId(myFamily.getId());;
        return myFamily;
    }

    public Family GetFamily(String userName) {
        Integer userFamilyId = userRepository.findByUsername(userName).get().getFamilyId();
        return familyRepository.findById(userFamilyId).get();
    }
}