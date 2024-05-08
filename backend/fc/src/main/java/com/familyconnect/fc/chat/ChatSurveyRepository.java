package com.familyconnect.fc.chat;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ChatSurveyRepository extends JpaRepository<ChatSurvey, Integer>{

    
    
}