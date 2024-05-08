package com.familyconnect.fc.chat;

import com.familyconnect.fc.family.Family;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.*;



@Entity
public class ChatSurvey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String senderUsername;
    private String senderName;
    private List<String> survey;
    private String timestamp;
    private String description;

    @ElementCollection
    private Map<String, Integer> surveyResults  = new HashMap<String, Integer>(); // map username to selected option

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family;

    public ChatSurvey(String senderUsername,String senderName ,List<String> survey, String timestamp, Family family, String description) {
        super();
        this.senderUsername = senderUsername;
        this.senderName = senderName;
        this.family = family;
        this.survey = survey;
        this.timestamp = timestamp;
        this.description = description;
    }



    public ChatSurvey() {
        super();
    }

    // Getter ve Setter metotları
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String sender) {
        this.senderUsername = sender;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family receiverFamily) {
        this.family = receiverFamily;
    }

    public List<String> getSurvey() {
        return survey;
    }

    public void setSurvey(List<String> survey) {
        this.survey = survey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Integer> getSurveyResults() {
        return surveyResults;
    }

    public void setSurveyResults(Map<String, Integer> surveyResults) {
        this.surveyResults = surveyResults;
    }

    public void removeSurveyResult(String username) {
        surveyResults.remove(username);
    }

    public void updateSurveyResult(String username, Integer option) {
        if (surveyResults.containsKey(username))
        {
            surveyResults.replace(username, option);
        }else {
            surveyResults.put(username, option);
        }
    }

}
