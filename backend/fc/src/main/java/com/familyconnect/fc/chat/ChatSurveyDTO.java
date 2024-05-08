package com.familyconnect.fc.chat;

import java.util.List;


public class ChatSurveyDTO {

    
    private String sender;
    private String description;
    private List<String> survey;
    private String timestamp;
     

    public ChatSurveyDTO() {
    }

    public ChatSurveyDTO(String sender, List<String> survey, String description, String timestamp) {
        this.sender = sender;
        this.survey = survey;
        this.timestamp = timestamp;
        this.description = description;
    }

    public String getSender() {
        return sender;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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

}
