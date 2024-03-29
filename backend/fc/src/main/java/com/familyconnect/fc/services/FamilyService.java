package com.familyconnect.fc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.familyconnect.fc.models.Family;
import com.familyconnect.fc.models.FamilyRequestDTO;
import com.familyconnect.fc.repository.FamilyRepository;


@Service
@Transactional
public class FamilyService {

    @Autowired
    private  FamilyRepository familyRepository;


    public Family Createfamily(FamilyRequestDTO family) {
        Family myFamily = new Family(family.getFamilyName());
        familyRepository.save(myFamily);
        return myFamily;
    }
}