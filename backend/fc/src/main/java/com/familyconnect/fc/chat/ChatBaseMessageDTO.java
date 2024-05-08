package com.familyconnect.fc.chat;
import java.util.*; 


public class ChatBaseMessageDTO {
    private Integer id;
    private String type;
    private String senderUsername;
    private String senderName;
    private String message;
    private String description;
    private List<String> survey;
    private Map<String, Integer> surveyResults  = new HashMap<String, Integer>(); // map username to selected option


    private String timestamp;


    public ChatBaseMessageDTO(Integer id,String type, String senderUsername, String senderName, String message,  String timestamp, String description, List<String> survey,Map<String, Integer> surveyResults) {
        super();
        this.id = id;
        this.type = type;
        this.senderUsername = senderUsername;
        this.senderName = senderName;
        this.timestamp = timestamp;

        if (type.equals("survey")) {
            FillSurvey( description, survey, surveyResults);
        }else if (type.equals("message")) {
            FillMessage(message);
        }
    }

    private void FillMessage(String message) {
        this.message = message;
    }

    private void FillSurvey(String description, List<String> survey, Map<String, Integer> surveyResults) {
        this.description = description;
        this.survey = survey;
        this.surveyResults = surveyResults;
    }

    public ChatBaseMessageDTO() {
        super();
    }

    // Getter ve Setter metotları

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String sender) {
        this.senderUsername = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getSurvey() {
        return survey;
    }

    public void setSurvey(List<String> survey) {
        this.survey = survey;
    }

    public Map<String, Integer> getSurveyResults() {
        return surveyResults;
    }

    public void setSurveyResults(Map<String, Integer> surveyResults) {
        this.surveyResults = surveyResults;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
